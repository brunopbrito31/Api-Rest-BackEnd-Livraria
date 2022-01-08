package com.brunopbrito31.apilivros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Informative;
import com.brunopbrito31.apilivros.services.InformativeService;


@RestController
@RequestMapping("/informatives")
public class InformativeController {

    @Autowired
    private InformativeService informativeService;

    @GetMapping
    public ResponseEntity<List<Informative>> getAll(){
        List<Informative> informatives = informativeService.getAllInformatives();
        if(informatives.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(informatives);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Informative> getByID(@PathVariable Long id){
        Optional<Informative> searschedInformative = informativeService.getInformativeById(id);
        if(!searschedInformative.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(searschedInformative.get());
    }

    @PostMapping
    public ResponseEntity<Informative> insert(@RequestBody Informative informative){

        System.out.println("Valor passado pelo front: "+informative);

        informative = informativeService.insertInformative(informative);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(informative.getId()).toUri();
        return ResponseEntity.created(uri).body(informative);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        informativeService.deleteInformative(id);
        return ResponseEntity.noContent().build();
    }
}
