package com.brunopbrito31.apilivros.repositories;

import com.brunopbrito31.apilivros.models.entities.SaleItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    
}
