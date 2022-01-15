package com.brunopbrito31.apilivros.repositories;

import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Product;
import com.brunopbrito31.apilivros.models.entities.StockItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, Long> {

    public Optional<StockItem> getStockItemByProduct(Product product);
    
}
