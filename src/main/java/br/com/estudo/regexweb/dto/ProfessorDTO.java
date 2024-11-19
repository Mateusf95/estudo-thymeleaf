package br.com.estudo.regexweb.dto;

import br.com.estudo.regexweb.models.Professor;
import br.com.estudo.regexweb.models.StatusProfessor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

public record ProfessorDTO(
        @NotBlank @NotNull String nome,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal salario,
        StatusProfessor status
) {
    public ProfessorDTO(final String nome, final BigDecimal salario, final StatusProfessor status) {
        this.nome = nome;
        this.salario = salario;
        this.status = status;
    }

    public Professor toProfessor() {
        Professor professor = new Professor();
        professor.setNome(nome);
        professor.setSalario(salario);
        professor.setStatusProfessor(status);

        return professor;
    }



    @Override
    public String toString() {
        return "ProfessorDTO{" +
                "nome='" + nome + '\'' +
                ", salario=" + salario +
                ", status=" + status +
                '}';
    }
}
