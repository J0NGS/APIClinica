package com.j0ngs.APIClinica.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.j0ngs.APIClinica.model.Medico;
import com.j0ngs.APIClinica.service.MedicoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/Medico")
public class MedicoController {
    private final MedicoService service;

    @Autowired
    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Medico> create(@RequestBody Medico entity) {
        return service.createMedico(entity);
    }

    @GetMapping
    public ResponseEntity<List<Medico>> getAllMedicos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int quantity) {
        return service.getAllMedicos(page, quantity);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Medico>> getMedicoByName(@RequestParam String name) {
        return service.getMedicoByName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> getMedicoById(@PathVariable UUID id) {
        return service.getMedicoById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> updateMedico(
            @PathVariable UUID id,
            @RequestBody Medico updatedMedico) {
        return service.updateMedico(id, updatedMedico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable UUID id) {
        service.deleteMedico(id);
        return ResponseEntity.noContent().build();
    }
}

