package com.brunopbrito31.apilivros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Contact;
import com.brunopbrito31.apilivros.services.ContactService;

// @CrossOrigin
@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> getAll(){
        List<Contact> contacts = contactService.getAllContacts();
        if(contacts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getByID(@PathVariable Long id){
        Optional<Contact> searschedContact = contactService.getContactById(id);
        if(!searschedContact.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(searschedContact.get());
    }

    @PostMapping
    public ResponseEntity<Contact> insert(@RequestBody Contact contact){

        System.out.println("Valor passado pelo front: "+contact);

        contact = contactService.insertContact(contact);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(contact.getId()).toUri();
        return ResponseEntity.created(uri).body(contact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

}
