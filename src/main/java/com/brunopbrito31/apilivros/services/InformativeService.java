package com.brunopbrito31.apilivros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Informative;
import com.brunopbrito31.apilivros.repositories.InformativeRepository;

@Service
public class InformativeService {

    @Autowired
    private InformativeRepository informativeRepository;

    public List<Informative> getAllInformatives() {
        return informativeRepository.findAll();
    }

    public Optional<Informative> getInformativeById(Long id){
        return informativeRepository.findById(id);
    }

    public Informative insertInformative(Informative informative){
        informative = informativeRepository.save(informative);
        return informative;
    }

    public void deleteInformative(Long id){
        informativeRepository.deleteById(id);
    }
}
