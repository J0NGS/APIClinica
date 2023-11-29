package com.j0ngs.APIClinica.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

import com.j0ngs.APIClinica.model.Agendamento;
import com.j0ngs.APIClinica.model.Medico;
import com.j0ngs.APIClinica.model.Paciente;
import com.j0ngs.APIClinica.model.DTO.AgendamentoRequest;
import com.j0ngs.APIClinica.repository.AgendamentoRepository;

@Service
public class AgendamentoService {
    private AgendamentoRepository repository;
    private MedicoService medicoService;
    private PacienteService pacienteService;

    @Autowired
    public AgendamentoService(AgendamentoRepository repository, MedicoService medicoService, PacienteService pacienteService) {
        this.repository = repository;
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
    }
    

    public ResponseEntity<Agendamento> getAgendamentoById(UUID id) {
        if (repository.findById(id).isPresent()) {
            return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento not found");
        }
    }

    public ResponseEntity<List<Agendamento>> getAgendamentosByMedico(String crm) {
        Medico medico = medicoService.getMedicoByCrm(crm).getBody();
        List<Agendamento> list = repository.findByMedico(medico);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamentos not found for the specified Medico");
        }
    }

    public ResponseEntity<List<Agendamento>> getAgendamentosByPaciente(String pacienteCpf) {
        Paciente paciente = pacienteService.getPacienteByCpf(pacienteCpf).getBody(); 
        List<Agendamento> list = repository.findByPaciente(paciente);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamentos not found for the specified Paciente");
        }
    }

    public ResponseEntity<List<Agendamento>> getAgendamentosByDateRange(String start, String end) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(start, formatter);
            LocalDateTime endTime = LocalDateTime.parse(end, formatter);

            List<Agendamento> list = repository.findByDataHoraBetween(startTime, endTime);

            if (!list.isEmpty()) {
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamentos not found within the specified date range");
            }
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Please use dd/MM/yyyy HH:mm:ss");
        }
    }


    public ResponseEntity<List<Agendamento>> getAllAgendamentos(int page, int quantity) {
        Pageable pageable = PageRequest.of(page, quantity);
        Page<Agendamento> response = repository.findAll(pageable);

        List<Agendamento> list = new ArrayList<>();

        list = response.getContent();

        if (response.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page is empty");
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    public ResponseEntity<Agendamento> createAgendamento(AgendamentoRequest agendamento) {
        Medico medico = medicoService.getMedicoByCrm(agendamento.getMedico()).getBody();
        Paciente paciente = pacienteService.getPacienteByCpf(agendamento.getPaciente()).getBody();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime data =  LocalDateTime.parse(agendamento.getDataHora(), formatter);
        Agendamento entity = new Agendamento(medico, paciente, data);
        repository.save(entity);
        return ResponseEntity.ok(entity);
    }

    public ResponseEntity<Agendamento> updateAgendamento(UUID id, Agendamento updatedAgendamento) {
        if (repository.findById(id).isPresent()) {
            Agendamento agendamento = repository.findById(id).get();
            agendamento.setMedico(updatedAgendamento.getMedico());
            agendamento.setPaciente(updatedAgendamento.getPaciente());
            agendamento.setDataHora(updatedAgendamento.getDataHora());
            // Atualizar outros campos conforme necessário

            return ResponseEntity.ok(repository.save(agendamento));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento not found");
        }
    }

    public void deleteAgendamento(UUID id) {
        repository.deleteById(id);
    }
}
