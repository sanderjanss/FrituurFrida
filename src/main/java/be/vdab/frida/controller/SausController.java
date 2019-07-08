package be.vdab.frida.controller;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.forms.SausRadenForm;
import be.vdab.frida.services.SausService;
import be.vdab.frida.sessions.SausRaden;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Controller
@RequestMapping("sauzen")
public class SausController {
    //------------------------------------------------------------------------ VARIABELEN

//-------------------------------------Wordt niet meer opgeroepen
//    Saus [] sauzen = new Saus[]{
//            new Saus(0, "cocktail", new String[]{"mayonaise", "ketchup", "whiskey"}),
//            new Saus(1, "mayonaise", new String[]{"eidooier", "olie"}),
//            new Saus(2, "mosterd", new String[]{"mosterdzaad", "witte wijnazijn", "kurkuma", "rietsuiker"}),
//            new Saus(3, "tartare", new String[]{"eieren", "sjalot", "peterselie", "bieslook", "dragon", "augurken", "mayonaise"}),
//            new Saus(4, "vinaigrette", new String[]{"azijn", "zout", "olie", "honing", "mosterd"})
//    };
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private SausService sausService;
    private final SausRaden sausRaden;


    //------------------------------------------------------------------------- METHODEN en CONSTRUCTORS


    public SausController(SausService sausService, SausRaden sausRaden) {
        for(char letter = 'a'; letter <= 'z'; letter++){
            alfabet[letter - 'a'] = letter;
        }
        this.sausService = sausService;
        this.sausRaden = sausRaden;
    }

    @GetMapping
    public ModelAndView toonSauzen(){
        return new ModelAndView("sauzen", "sauzen", sausService.findAll());
    }
    @GetMapping("{id}")
    public ModelAndView toonDetailSausje(@PathVariable long id){
        ModelAndView modelAndView = new ModelAndView("sausje");
        sausService.findById(id).ifPresent(sausje -> modelAndView.addObject("sausje", sausje));
        return modelAndView;
    }
    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet);
    }
    @GetMapping("alfabet/{letter}")
    public ModelAndView sauzenBeginnendMet(@PathVariable char letter) {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet)
                .addObject("sauzen", sausService.findByNaamBegintMet(letter));
    }

    private String randomSaus() {
        List<Saus> sauzen = sausService.findAll();
        return sauzen.get(
                ThreadLocalRandom.current().nextInt(sauzen.size())).getNaam();
    }
    @GetMapping("raden")
    public ModelAndView radenForm() {
        sausRaden.reset(randomSaus());
        return new ModelAndView("sausRaden").addObject(sausRaden)
                .addObject(new SausRadenForm(null));
    }
    @PostMapping("raden/nieuwspel")
    public String radenNieuwSpel() {
        return "redirect:/sauzen/raden";
    }
    @PostMapping(value = "raden")
    public ModelAndView raden(@Valid SausRadenForm form, Errors errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("sausRaden").addObject(sausRaden);
        }
        sausRaden.doeGok(form.getLetter());
        return new ModelAndView("redirect:/sauzen/raden/volgendegok");
    }
    @GetMapping("raden/volgendegok")
    public ModelAndView volgendeGok() {
        return new ModelAndView("sausRaden").addObject(sausRaden)
                .addObject(new SausRadenForm(null));
    }





}
