package be.vdab.frida.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcGastenBoekRepository.class)
@Sql("/insertGastenBoek.sql")
public class JdbcGastenBoekRepositoryTest extends
        AbstractTransactionalJUnit4SpringContextTests {
    private static final String GASTENBOEK = "gastenboek";
    @Autowired
    private JdbcGastenBoekRepository repository;

    private long idVanTestEntry(){
        String sql = "select id from gastenboek where naam = 'test'";
        return super.jdbcTemplate.queryForObject(sql, Long.class);
    }
    @Test
    public void create() {
        long id = idVanTestEntry();
        assertThat(id).isPositive();
        assertThat(super.countRowsInTableWhere(GASTENBOEK, "id=" + id)).isOne();
    }
    @Test
    public void findAll() {
        assertThat(repository.findAll()).hasSize(super.countRowsInTable(GASTENBOEK))
                .extracting(entry -> entry.getDatum())
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    public void delete(){
        long id = idVanTestEntry();
        repository.delete(id);
        assertThat(super.countRowsInTableWhere(GASTENBOEK, "id=" +id)).isZero();
    }
}
