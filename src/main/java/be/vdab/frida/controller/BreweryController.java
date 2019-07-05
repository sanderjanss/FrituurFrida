package be.vdab.frida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("brewery")
public class BreweryController {

    @GetMapping
    public ModelAndView zoekMachine(){
        return new ModelAndView("brewery");
    }
}
