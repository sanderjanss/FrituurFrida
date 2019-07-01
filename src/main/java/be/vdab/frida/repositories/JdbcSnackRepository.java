package be.vdab.frida.repositories;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.exceptions.snackNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcSnackRepository implements SnackRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<Snack> snackMapper =
            (result, rowNum) ->
            new Snack(result.getLong("id"), result.getString("naam"),
            result.getBigDecimal("prijs"));

    public JdbcSnackRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template);
        insert.withTableName("snacks");
        insert.usingGeneratedKeyColumns("id"); //automatisch gegenereerde primary key
    }

    @Override
    public Optional<Snack> findById(long id) {
        try{
            String sql = "select id, naam, prijs from snacks where id =?";
            return Optional.of(template.queryForObject(sql, snackMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex){
            return Optional.empty();
        }
    }

    @Override
    public void update(Snack snack) {
        String sql = "update snacks set naam=?, prijs=? where id=?";
        if(template.update(sql, snack.getNaam(), snack.getPrijs(), snack.getId()) == 0){
            throw new snackNietGevondenException();
        }
    }

    @Override
    public List<Snack> findByBeginNaam(String letter) {
        String sql ="select id, naam, prijs from snacks where naam like ? order by naam";
        return template.query(sql, snackMapper, letter + '%');
    }

    @Override
    public List<Snack> findAll() {
        String sql = "select id, naam, prijs from snacks order by id";
        return template.query(sql, snackMapper);
    }

    @Override
    public List<Snack> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
        String sql = "select id, naam, prijs from snacks where prijs between ? and ? order by prijs";
        return template.query(sql, snackMapper, van, tot);
    }
}
