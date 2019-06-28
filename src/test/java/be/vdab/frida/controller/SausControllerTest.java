package be.vdab.frida.controller;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.services.SausService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class SausControllerTest {
    private SausController controller;
    @Mock
    private SausService sausService;

    @Before
    public void Before() {
        when(sausService.findById(3)).thenReturn(Optional.of(new Saus(3L, "", null)));
        controller = new SausController(sausService);
    }

    @Test
    public void sauzenGebruiktDeThymeleafPagina() {
        assertThat(controller.toonSauzen().getViewName()).isEqualTo("sauzen");
    }

    @Test
    public void sauzenGeeftSauzenDoorAanDeThymeleafPagina() {
        assertThat(controller.toonSauzen().getModel().get("sauzen")).isInstanceOf(List.class);
    }

    @Test
    public void sausGebruiktDeThymeleafPaginaSaus() {
        assertThat(controller.toonDetailSausje(3).getViewName()).isEqualTo("sausje");
    }

    @Test
    public void sausGeeftGevondenSausDoorAanDeThymeleafPagina() {
        Saus saus = (Saus) controller.toonDetailSausje(3).getModel().get("sausje");
        assertThat(saus.getNummer()).isEqualTo(3);
    }

    @Test
    public void sausGeeftOnbestaandeSausNietDoorAanDeThymeleafPagina() {
        assertThat(controller.toonDetailSausje(-1).getModel()).doesNotContainKeys("sausje");
    }

}
