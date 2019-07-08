package be.vdab.frida.controller;

import be.vdab.frida.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("talen")
public class TaalController {

    @GetMapping
    public ModelAndView nederlands(@RequestHeader("Accept-Language") String language, @CookieValue(name = "kleur", required = false) String kleur) {
        ModelAndView modelAndView = new ModelAndView("talen", "nederlands", language.startsWith("nl"));
        modelAndView.addObject("kleur", kleur);
        return modelAndView;
    }
}
