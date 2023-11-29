package com.j0ngs.APIClinica.model;

import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty
    @NotNull
    private String nome;
    @NotEmpty
    @NotNull
    @Column(unique = true)
    @Size(max = 6, min = 6)
    private String crm;

    @NotEmpty
    @NotNull
    private String especialidade;

    // Relacionamento: Um médico pode ter muitos agendamentos
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;
}
