package com.brunopbrito31.apilivros.repositories;

import com.brunopbrito31.apilivros.models.entities.Product;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductPageableRepository extends PagingAndSortingRepository<Product, Long>{
    
}
