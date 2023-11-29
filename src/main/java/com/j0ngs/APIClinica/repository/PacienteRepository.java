package com.j0ngs.APIClinica.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.j0ngs.APIClinica.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, UUID>{
    public Optional<Paciente> findById(UUID id);
    public Optional<Paciente> findByCpf(String cpf);
    public Optional<Paciente> findByEmail(String email);        
    public Optional<List<Paciente>> findByNomeIgnoreCaseContaining(String name);
    public Page<Paciente> findAll(Pageable pageable);
}