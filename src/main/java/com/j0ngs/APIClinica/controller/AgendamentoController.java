package com.j0ngs.APIClinica.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.j0ngs.APIClinica.model.Agendamento;
import com.j0ngs.APIClinica.model.DTO.AgendamentoRequest;
import com.j0ngs.APIClinica.service.AgendamentoService;

@RestController
@RequestMapping("/Agendamento")
public class AgendamentoController {
    private final AgendamentoService service;

    @Autowired
    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Agendamento> create(@RequestBody AgendamentoRequest entity) {
        return service.createAgendamento(entity);
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> getAllAgendamentos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int quantity) {
        return service.getAllAgendamentos(page, quantity);
    }

    @GetMapping("/search/medico")
    public ResponseEntity<List<Agendamento>> getAgendamentosByMedico(@RequestParam String medicoCrm) {
        return service.getAgendamentosByMedico(medicoCrm);
    }

    @GetMapping("/search/paciente")
    public ResponseEntity<List<Agendamento>> getAgendamentosByPaciente(@RequestParam String pacienteCpf) {
        return service.getAgendamentosByPaciente(pacienteCpf);
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<Agendamento>> getAgendamentosByDateRange(
            @RequestParam String start,
            @RequestParam String end) {
        return service.getAgendamentosByDateRange(start, end);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> getAgendamentoById(@PathVariable UUID id) {
        return service.getAgendamentoById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> updateAgendamento(
            @PathVariable UUID id,
            @RequestBody Agendamento updatedAgendamento) {
        return service.updateAgendamento(id, updatedAgendamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendamento(@PathVariable UUID id) {
        service.deleteAgendamento(id);
        return ResponseEntity.noContent().build();
    }
}