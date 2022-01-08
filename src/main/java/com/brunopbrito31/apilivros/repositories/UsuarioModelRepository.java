package com.brunopbrito31.apilivros.repositories;

import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.UsuarioModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioModelRepository extends JpaRepository<UsuarioModel, Long> {
    

    public Optional<UsuarioModel> findByLogin(String login);
}
