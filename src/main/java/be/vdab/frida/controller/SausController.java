package be.vdab.frida.controller;

import be.vdab.frida.domain.Saus;
import be.vdab.frida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
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


    //------------------------------------------------------------------------- METHODEN en CONSTRUCTORS


    public SausController(SausService sausService) {
        this.sausService = sausService;
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





}
