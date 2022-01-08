package com.brunopbrito31.apilivros.services;

import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Category;
import com.brunopbrito31.apilivros.models.entities.Product;
import com.brunopbrito31.apilivros.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public Optional<Product> getProductById(Long id){
        Optional<Product> productSearched = productRepository.findById(id);
        return productSearched;
    }

    public Product saveProduct (Product product){
        product = productRepository.save(product);
        return product;
    }

    public Product addCategory (Category category, Product product){
        product.setCategory(category);
        product = productRepository.save(product);
        return product;
    }
    
}
