package com.brunopbrito31.apilivros.repositories;

import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    

    @Query(
        value = "select id, date, status, total, expiration, id_user from tb_sales WHERE id=?1 expiration > NOW() ",
        nativeQuery = true
    )
    Optional<Sale> getSaleByClientAndExpiration(Long id);
}
