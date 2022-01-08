package com.brunopbrito31.apilivros.repositories;

import com.brunopbrito31.apilivros.models.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
