package com.brunopbrito31.apilivros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Contact;
import com.brunopbrito31.apilivros.repositories.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id){
        return contactRepository.findById(id);
    }

    public Contact insertContact(Contact contact){
        contact = contactRepository.save(contact);
        return contact;
    }

    public void deleteContact(Long id){
        contactRepository.deleteById(id);
    }

    //Colocar um metodo de atualizacao
}
