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

import com.j0ngs.APIClinica.model.Medico;
import com.j0ngs.APIClinica.repository.MedicoRepository;

@Service
public class MedicoService {
    private MedicoRepository repository;

    @Autowired
    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<Medico>> getMedicoByName(String name){
        if(repository.findByNomeIgnoreCaseContaining(name).isPresent()){
            List<Medico> list = new ArrayList<>();
            list = repository.findByNomeIgnoreCaseContaining(name).get();

            return new ResponseEntity<>(list, HttpStatus.OK);
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medico not fount");
    }

    public ResponseEntity<List<Medico>> getAllMedicos(int page, int quantity) {
        Pageable pageable = PageRequest.of(page, quantity);
        Page<Medico> response = repository.findAll(pageable);

        List<Medico> list = new ArrayList<>();

        list = response.getContent();

        if(response.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page is empty");
        else 
            return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<Medico> getMedicoById(UUID id) {
        if (repository.findById(id).isPresent())
            return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);   
        else 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medico Not Found");
        
    }

    public ResponseEntity<Medico> getMedicoByCrm(String crm) {
        if (repository.findByCrm(crm).isPresent())
            return new ResponseEntity<>(repository.findByCrm(crm).get(), HttpStatus.OK);   
        else 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medico Not Found");
        
    }

    public ResponseEntity<Medico> createMedico(Medico medico) {
        return ResponseEntity.ok(repository.save(medico));
    }

    public ResponseEntity<Medico> updateMedico(UUID id, Medico updatedMedico) {
        if (repository.findById(id).isPresent()) {
            Medico medico = repository.findById(id).get();
            medico.setNome(updatedMedico.getNome());
            medico.setEspecialidade(updatedMedico.getEspecialidade());
            return ResponseEntity.ok(repository.save(medico));
        }else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medico not found");
    }

    public void deleteMedico(UUID id) {
        repository.deleteById(id);
    }
}

