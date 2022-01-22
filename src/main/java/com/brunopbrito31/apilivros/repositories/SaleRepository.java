package com.brunopbrito31.apilivros.repositories;

import com.brunopbrito31.apilivros.models.entities.Sale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    
}
