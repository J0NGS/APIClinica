package com.j0ngs.APIClinica.model.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AgendamentoRequest {
    private String paciente;
    private String medico;
    private String dataHora;
}
