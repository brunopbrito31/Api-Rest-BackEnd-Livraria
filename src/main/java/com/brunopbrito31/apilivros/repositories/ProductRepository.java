package com.brunopbrito31.apilivros.repositories;

import com.brunopbrito31.apilivros.models.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
