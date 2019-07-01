package be.vdab.frida.controller;

import be.vdab.frida.forms.VanTotPrijsForm;
import be.vdab.frida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping("snacks")
public class SnackController {
    private final SnackService snackService;

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    @GetMapping
    public ModelAndView toonSnacks(@CookieValue(name = "kleur", required = false) String kleur, @CookieValue(name = "reedsBezocht", required = false) String reedsBezocht){
        ModelAndView modelAndView = new ModelAndView("snacks", "snacks", snackService.findAll());
        modelAndView.addObject("kleur", kleur);
        if (reedsBezocht != null) {
            modelAndView.addObject("reedsBezocht", true);
        }
        modelAndView.addObject("aantalSnacks", snackService.findAantalSnacks());
        return modelAndView;
    }

    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("snacksAlfabet", "alfabet", alfabet);
    }
    @GetMapping("alfabet/{letter}")
    public ModelAndView findByBeginLetter(@PathVariable String letter) {
        return new ModelAndView("snacksAlfabet", "alfabet", alfabet)
                .addObject("snacks", snackService.findByBeginNaam(letter));
    }

    @GetMapping("vantotprijs/form")
    public ModelAndView vanTotPrijsForm(){
      return  new ModelAndView("vantotprijs").addObject(new VanTotPrijsForm(null, null));
    }

    @GetMapping("vantotprijs")
    public ModelAndView vanTotPrijs(@Valid VanTotPrijsForm form, Errors errors){
        ModelAndView modelAndView = new ModelAndView("vantotprijs");
        if(errors.hasErrors()){
            return modelAndView;
        }
        return modelAndView.addObject("snacks", snackService.findByPrijsBetween(form.getVan(), form.getTot()));
    }
}
