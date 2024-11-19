package br.com.estudo.regexweb.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @GetMapping("/hello-model")
    public String hello(Model model) {
        model.addAttribute("nome", "Mateus Model!");
        return "hello";
    }
    @GetMapping("/hello-servelet")
    public String hello(HttpServletRequest request) {
        request.setAttribute("nome", "Mateus Servelet!");
        return "hello";
    }

    @GetMapping("/hello")
    public ModelAndView hello() {
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("nome", "Mateus Model And View!");
        return mv;
    }
}
