package br.com.estudo.regexweb.controllers;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("currentURI", request.getRequestURI());
        return mv;
    }
}
