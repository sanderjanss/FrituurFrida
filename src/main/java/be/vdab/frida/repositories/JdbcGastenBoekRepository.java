package be.vdab.frida.repositories;

import be.vdab.frida.domain.GastenBoekEntry;
import be.vdab.frida.domain.Snack;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcGastenBoekRepository implements GastenBoekRepository {
    private final SimpleJdbcInsert insert;
    private final JdbcTemplate template;

    public JdbcGastenBoekRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template);
        insert.withTableName("gastenboek");
        insert.usingGeneratedKeyColumns("id");
    }

    @Override
    public long create(GastenBoekEntry entry) {
        Map<String, Object> kolomWaarden = new HashMap<>();
        kolomWaarden.put("naam", entry.getNaam());
        kolomWaarden.put("datum", entry.getDatum());
        kolomWaarden.put("bericht", entry.getBericht());
        Number id = insert.executeAndReturnKey(kolomWaarden);
        return id.longValue();
    }

    private final RowMapper<GastenBoekEntry> gastenMapper =
            (result, rowNum) -> new GastenBoekEntry(result.getLong("id"), result.getString("naam"),
                    result.getDate("datum").toLocalDate(), result.getString("bericht"));

    @Override
    public List<GastenBoekEntry> findAll() {
        String sql = "select id, naam, datum, bericht from gastenboek order by datum desc";
        return template.query(sql, gastenMapper);
    }

    @Override
    public void delete(long id) {
        String sql = "delete from gastenboek where id=?";
        template.update(sql, id);
    }

}
