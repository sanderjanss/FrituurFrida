package be.vdab.frida.controller;

import be.vdab.frida.domain.Adres;
import be.vdab.frida.domain.Gemeente;
import be.vdab.frida.sessions.Identificatie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

@Controller
@RequestMapping("/")
class IndexController {
    private Calendar c = Calendar.getInstance();
    private int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

    public String welkeDagZijnWe (){
        if (dayOfWeek == 2 || dayOfWeek == 5){
            return "Gesloten";
        }
        else {
            return "Open";
        }
    }

    @GetMapping
    public ModelAndView index (@CookieValue(name = "reedsBezocht", required = false)
            String reedsBezocht, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("index", "index", welkeDagZijnWe());
        modelAndView.addObject("adres", new Adres("Vettige Frietjes Laan", "33"));
        modelAndView.addObject("gemeente", new Gemeente("Antwerpen", 2000));

        Cookie cookie = new Cookie("reedsBezocht", "ja");
        cookie.setMaxAge(31_536_000);
        cookie.setPath("/");
        response.addCookie(cookie);
        if (reedsBezocht != null) {
            modelAndView.addObject("reedsBezocht", true);
        }

        return modelAndView;
    }




}
