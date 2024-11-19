package br.com.estudo.regexweb.controllers;

import br.com.estudo.regexweb.dto.ProfessorDTO;
import br.com.estudo.regexweb.models.Professor;
import br.com.estudo.regexweb.models.StatusProfessor;
import br.com.estudo.regexweb.repositories.ProfessorRepository;
import br.com.estudo.regexweb.services.ProfessorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository repository;

    @Autowired
    private ProfessorService service;

    @GetMapping
    public ModelAndView index(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("professores/index");


        List<Professor> professores = repository.findAll();

        mv.addObject("professores", professores);
        mv.addObject("currentURI", request.getRequestURI());
        return mv;
    }

    @GetMapping("/new")
    public ModelAndView newProfessor(ProfessorDTO professor, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("listStatus", StatusProfessor.values());
        mv.addObject("currentURI", request.getRequestURI());
        return mv;
    }

    @PostMapping
    public ModelAndView create(@Valid ProfessorDTO professor, BindingResult result) {
        if(result.hasErrors()){
            ModelAndView mv = new ModelAndView("redirect:/professores/new");
            mv.addObject("listStatus", StatusProfessor.values());

            return mv;
        } else {

            Professor newProfessor = professor.toProfessor();

            repository.save(newProfessor);

            return new ModelAndView("redirect:/professores");

        }

    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id, HttpServletRequest request){

        Optional<Professor> professor = repository.findById(id);
        if(professor.isPresent()){
            ModelAndView mv = new ModelAndView("professores/show");

            mv.addObject("currentURI", request.getRequestURI());
            mv.addObject("professor", professor.get());
            return mv;
        }else {
            return new ModelAndView("redirect:/professores");
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, ProfessorDTO professorDTO) {
        Optional<Professor> professor = repository.findById(id);

        if(professor.isPresent()){
            ModelAndView mv = new ModelAndView("professores/edit");
            ProfessorDTO prof = service.fromProfessor(professor.get());

            mv.addObject("professorDTO", prof);
            mv.addObject("professorId", professor.get().getId());
            mv.addObject("listStatus", StatusProfessor.values());

            return mv;
        }else{
            ModelAndView mv = new ModelAndView("redirect:/professores");
            mv.addObject("mensagem", "Professor com id: "+ id + " não encontrado!");
            mv.addObject("erro", true);
            return mv;
        }
    }

    @PostMapping("/{id}")
   public ModelAndView update(@PathVariable Long id, @Valid ProfessorDTO professorDTO, BindingResult result) {
        if(result.hasErrors()) {
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", id);
            mv.addObject("listStatus", StatusProfessor.values());

            return mv;
        }else {
            Optional<Professor> professor = repository.findById(id);

            if(professor.isPresent()){
                Professor prof = service.toProfessor(professor.get(), professorDTO);

                repository.save(prof);
                return  new ModelAndView("redirect:/professores/"+ prof.getId());
            }else{
                ModelAndView mv = new ModelAndView("redirect:/professores");
                mv.addObject("mensagem", "Professor com id: "+ id + " não encontrado!");
                mv.addObject("erro", true);
                return mv;
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/professores");
        try{
            repository.deleteById(id);
            mv.addObject("mensagem", "Professor com id: "+ id + " deletado com suceso!");
            mv.addObject("erro", false);
        }catch (EmptyResultDataAccessException e){
            System.out.println(e);
            mv.addObject("mensagem", "Professor com id: "+ id + " não encontrado!");
            mv.addObject("erro", true);

        }
        return mv;
    }
}
