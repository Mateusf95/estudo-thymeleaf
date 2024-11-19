package br.com.estudo.regexweb.services;

import br.com.estudo.regexweb.dto.ProfessorDTO;
import br.com.estudo.regexweb.models.Professor;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    public ProfessorDTO fromProfessor(Professor professor) {
       return new ProfessorDTO(professor.getNome(),
                professor.getSalario(),
                professor.getStatusProfessor());
    }

    public Professor toProfessor(Professor professor, ProfessorDTO professorDTO) {
        professor.setId(professor.getId());
        professor.setNome(professorDTO.nome());
        professor.setSalario(professorDTO.salario());
        professor.setStatusProfessor(professorDTO.status());

        return professor;
    }
}
