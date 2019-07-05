package be.vdab.frida.controller;

import be.vdab.frida.domain.Snack;
import be.vdab.frida.services.SnackService;
import be.vdab.frida.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("mandje")
public class MandjeController {
    private Mandje mandje;
    private SnackService snackService;

    public MandjeController(Mandje mandje, SnackService snackService) {
        this.mandje = mandje;
        this.snackService = snackService;
    }

    @GetMapping
    public ModelAndView toonMandje(){
        List<Snack> alleSnacks = snackService.findAll();
        ModelAndView modelAndView = new ModelAndView("mandje").addObject("alleSnacks", alleSnacks);
        if(mandje.isGevuld()){
            modelAndView.addObject("snacksInMandje", alleSnacks.stream().filter(snack->mandje.bevat(snack.getId())).collect(Collectors.toList()));
        }
        return modelAndView;
    }
    @PostMapping("voegtoe")
    public String voegToe(long id){
        mandje.voegToe(id);
        return "redirect:/mandje";
    }

    @PostMapping("verwijder")
    public String verwijder(long id){
        mandje.verwijder(id);
        return "redirect:/mandje";
    }
}
