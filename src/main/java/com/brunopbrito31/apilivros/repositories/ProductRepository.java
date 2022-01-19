package com.brunopbrito31.apilivros.repositories;

import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    Optional<Product> getProductByBarcode(String barcode);

    @Query(
        value = "select tb_products.id, tb_products.description, tb_products.discount, tb_products.img_url, tb_products.price, tb_products.rating, tb_products.title, tb_products.barcode, tb_products.category_id, tb_categories.name from tb_products left join tb_categories on tb_products.category_id = tb_categories.id where title like %?%1",
        nativeQuery = true
    )
    List<Product> getProductByName(String name);

    @Query(
        value = "select count(id) from tb_products",
        nativeQuery = true
    )
    Integer getTotalProducts();

}
