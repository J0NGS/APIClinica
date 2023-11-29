package com.j0ngs.APIClinica.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.j0ngs.APIClinica.model.Paciente;
import com.j0ngs.APIClinica.repository.PacienteRepository;

@Service
public class PacienteService {
    private PacienteRepository repository;

    @Autowired
    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Paciente> getPacienteById(UUID id) {
        if (repository.findById(id).isPresent()) {
            return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente not found");
        }
    }

    public ResponseEntity<Paciente> getPacienteByCpf(String cpf) {
        if (repository.findByCpf(cpf).isPresent()) {
            return new ResponseEntity<>(repository.findByCpf(cpf).get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente not found");
        }
    }

    public ResponseEntity<Paciente> getPacienteByEmail(String email) {
        if (repository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>(repository.findByEmail(email).get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente not found");
        }
    }

    public ResponseEntity<List<Paciente>> getPacienteByName(String name) {
        if (repository.findByNomeIgnoreCaseContaining(name).isPresent()) {
            List<Paciente> list = repository.findByNomeIgnoreCaseContaining(name).get();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente not found");
        }
    }

    public ResponseEntity<List<Paciente>> getAllPacientes(int page, int quantity) {
        Pageable pageable = PageRequest.of(page, quantity);
        Page<Paciente> response = repository.findAll(pageable);

        List<Paciente> list = new ArrayList<>();

        list = response.getContent();

        if (response.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page is empty");
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    public ResponseEntity<Paciente> createPaciente(Paciente paciente) {
        return ResponseEntity.ok(repository.save(paciente));
    }

    public ResponseEntity<Paciente> updatePaciente(UUID id, Paciente updatedPaciente) {
        if (repository.findById(id).isPresent()) {
            Paciente paciente = repository.findById(id).get();
            paciente.setNome(updatedPaciente.getNome());
            paciente.setCpf(updatedPaciente.getCpf());
            paciente.setEmail(updatedPaciente.getEmail());
            // Atualizar outros campos conforme necessário
    
            return ResponseEntity.ok(repository.save(paciente));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente not found");
        }
    }

    public void deletePaciente(UUID id) {
        repository.deleteById(id);
    }
}
