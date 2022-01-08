package com.brunopbrito31.apilivros.services;

import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.UsuarioModel;
import com.brunopbrito31.apilivros.repositories.UsuarioModelRepository;
import com.brunopbrito31.data.DetalheUsuarioData;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class DetalheUsuarioServiceimpl implements UserDetailsService{

    private final UsuarioModelRepository repository;

    public DetalheUsuarioServiceimpl(UsuarioModelRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioModel> usuario = repository.findByLogin(username);

        if(!usuario.isPresent()) {
            throw new UsernameNotFoundException("Usuário ["+username+"] nao encontrado");
        }

        return new DetalheUsuarioData(usuario);
    }
    
}
