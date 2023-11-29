package com.j0ngs.APIClinica.model;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty
    @NotNull
    private String nome;
    @CPF
    @Column(unique = true)
    private String cpf;
    @Email
    @Column(unique = true)
    private String email;

    // Relacionamento: Um paciente pode ter muitos agendamentos
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;

    // Outros atributos, métodos e construtores conforme necessário
}
