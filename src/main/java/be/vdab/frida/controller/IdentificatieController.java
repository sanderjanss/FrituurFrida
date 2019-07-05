package be.vdab.frida.controller;

import be.vdab.frida.sessions.Identificatie;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("identificatie")
public class IdentificatieController {
    private Identificatie identificatie;

    public IdentificatieController(Identificatie identificatie) {
        this.identificatie = identificatie;
    }

    @GetMapping
    public ModelAndView identificatie(){
        return new ModelAndView("identificatie", "identificatie", identificatie);
    }

    @PostMapping
    public String identificatie(@Valid Identificatie identificatie, Errors errors){
        if(errors.hasErrors()){
            return "/identificatie";
        } else {
            this.identificatie.setEmailAdres(identificatie.getEmailAdres());
            return "redirect:/";
        }
    }

}
