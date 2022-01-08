package com.brunopbrito31.apilivros.repositories;

import com.brunopbrito31.apilivros.models.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
