package com.j0ngs.APIClinica.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.j0ngs.APIClinica.model.Agendamento;
import com.j0ngs.APIClinica.model.Medico;
import com.j0ngs.APIClinica.model.Paciente;


@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID>{
    Optional<Agendamento> findById(UUID id);
    List<Agendamento> findByMedico(Medico medico);
    List<Agendamento> findByPaciente(Paciente paciente);
    List<Agendamento> findByDataHoraAfter(LocalDateTime dataHora);
    List<Agendamento> findByDataHoraBefore(LocalDateTime dataHora);
    List<Agendamento> findByDataHoraBetween(LocalDateTime start, LocalDateTime end);
    Page<Agendamento> findAll(Pageable pageable);
}