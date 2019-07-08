package be.vdab.frida.controller;

import be.vdab.frida.forms.GastenBoekEntryForm;
import be.vdab.frida.services.GastenBoekService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("gastenboek")
public class GastenBoekController {
    private final GastenBoekService service;

    public GastenBoekController(GastenBoekService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView findAll(){
        return new ModelAndView("gastenboek", "gastenboek", service.findAll());
    }

    @GetMapping("toevoegen/form")
    public ModelAndView toevoegForm(){
        return new ModelAndView("gastenboek", "gastenboek", service.findAll()).addObject(new GastenBoekEntryForm("",""));
    }

    @PostMapping("toevoegen")
    public ModelAndView toevoegen(@Valid GastenBoekEntryForm form, Errors errors){
        if(errors.hasErrors()){
            return new ModelAndView("gastenboek", "gastenboek", service.findAll());
        }
        service.create(form);
        return new ModelAndView("redirect:/gastenboek");
    }
    @PostMapping("verwijderen")
    public String delete(long[] id) {
        if (id != null) {
            service.delete(id);
        }
        return "redirect:/gastenboek";
    }
}
