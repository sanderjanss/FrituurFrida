package be.vdab.frida.repositories;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.exceptions.snackNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcSnackRepository implements SnackRepository {
    private final JdbcTemplate template;
    private final RowMapper<Snack> snackMapper =
            (result, rowNum) ->
            new Snack(result.getLong("id"), result.getString("naam"),
            result.getBigDecimal("prijs"));

    public JdbcSnackRepository(JdbcTemplate template) {
        this.template = template;
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
    public List<Snack> findByBeginNaam(String beginNaam) {
        String sql ="select id, naam, prijs from snacks where naam like ? order by naam";
        return template.query(sql, snackMapper, beginNaam + '%');
    }
}
