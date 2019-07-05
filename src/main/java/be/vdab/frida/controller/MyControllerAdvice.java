package be.vdab.frida.controller;

import be.vdab.frida.sessions.Identificatie;
import be.vdab.frida.sessions.Mandje;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@org.springframework.web.bind.annotation.ControllerAdvice
public class MyControllerAdvice {
    private Identificatie identificatie;
    private Mandje mandje;

    public MyControllerAdvice(Identificatie identificatie, Mandje mandje) {
        this.identificatie = identificatie;
        this.mandje = mandje;
    }

    @ModelAttribute
    void extraDataToevoegenAanModel(Model model){
        model.addAttribute(identificatie);
        model.addAttribute(mandje);
    }
}
