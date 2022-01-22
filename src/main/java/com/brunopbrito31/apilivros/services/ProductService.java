package com.brunopbrito31.apilivros.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Category;
import com.brunopbrito31.apilivros.models.entities.Product;
import com.brunopbrito31.apilivros.repositories.ProductPageableRepository;
import com.brunopbrito31.apilivros.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPageableRepository repository;

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public List<Product> getAllProductsPageable(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy));
        Page<Product> pagedResult = repository.findAll(paging);

        return pagedResult.hasContent() ? 
            pagedResult.getContent() : 
            new ArrayList<Product>();          
    }

    public Optional<Product> getProductById(Long id){
        Optional<Product> productSearched = productRepository.findById(id);
        return productSearched;
    }

    public Optional<Product> getProductByBarcode(String barcode){
        Optional<Product> productSearched = productRepository.getProductByBarcode(barcode);
        return productSearched;
    }

    public List<Product> getProductByTitle(String title){
        List<Product> productsSearched = productRepository.getProductByName(title);
        return productsSearched;
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

    public Integer getTotalProducts(){
        return productRepository.getTotalProducts();
    }
    
}
