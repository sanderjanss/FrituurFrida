package be.vdab.frida.repositories;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.exceptions.snackNietGevondenException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcSnackRepository.class)
@Sql("/insertSnacks.sql")

public class JdbcSnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String SNACKS = "snacks";
    @Autowired
    private JdbcSnackRepository jdbcSnackRepository;

    private long idVanTestSnack(){
        return super.jdbcTemplate.queryForObject("select id from snacks where naam = 'test' ", Long.class);
    }

    @Test
    public void findById() {
        assertThat(jdbcSnackRepository.findById(idVanTestSnack()).get().getNaam()).isEqualTo("test");
    }

    @Test
    public void findByOnbestaandeId() {
        assertThat(jdbcSnackRepository.findById(-1)).isEmpty();
    }

    @Test
    public void update() {
        long id = idVanTestSnack();
        Snack snack = new Snack(id, "test", BigDecimal.ONE);
        jdbcSnackRepository.update(snack);
        assertThat(super.jdbcTemplate.queryForObject(
                "select prijs from snacks where id=?", BigDecimal.class, id)).isOne();
    }
    @Test
    public void updateOnbestaandeSnack() {
        assertThatExceptionOfType(snackNietGevondenException.class)
                .isThrownBy(() -> jdbcSnackRepository.update(new Snack(-1,"test",BigDecimal.ONE)));
    }
    @Test
    public void findByBeginNaam() {
        assertThat(jdbcSnackRepository.findByBeginNaam("t"))
                .hasSize(super.jdbcTemplate.queryForObject(
                        "select count(*) from snacks where naam like 't%'", Integer.class))
                .extracting(snack -> snack.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam.startsWith("t")))
                .isSorted();
    }


}
