package com.j0ngs.APIClinica.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.j0ngs.APIClinica.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID>{
    public Optional<Medico> findById(UUID id);
    public Optional<Medico> findByCrm(String crm);        
    public Optional<List<Medico>> findByNomeIgnoreCaseContaining(String name);
    public Page<Medico> findAll(Pageable pageable);
}