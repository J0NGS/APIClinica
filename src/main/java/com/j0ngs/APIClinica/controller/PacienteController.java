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

import com.j0ngs.APIClinica.model.Paciente;
import com.j0ngs.APIClinica.service.PacienteService;

@RestController
@RequestMapping("/Paciente")
public class PacienteController {
    private final PacienteService service;

    @Autowired
    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Paciente> create(@RequestBody Paciente entity) {
        return service.createPaciente(entity);
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> getAllPacientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int quantity) {
        return service.getAllPacientes(page, quantity);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Paciente>> getPacienteByName(@RequestParam String name) {
        return service.getPacienteByName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable UUID id) {
        return service.getPacienteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(
            @PathVariable UUID id,
            @RequestBody Paciente updatedPaciente) {
        return service.updatePaciente(id, updatedPaciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable UUID id) {
        service.deletePaciente(id);
        return ResponseEntity.noContent().build();
    }
}