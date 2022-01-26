package com.brunopbrito31.apilivros.repositories;

import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.SaleItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

    @Query(
        value = "select id, quantity, total_price, unit_price, sale_id, product_id from tb_sale_items WHERE sale_id=?1 AND product_id=?2 ",
        nativeQuery = true
    )
    Optional<SaleItem> findSaleITemBySaleAndProduct(Long idSale, Long idProd);
    
}
