package com.brunopbrito31.apilivros.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Product;
import com.brunopbrito31.apilivros.repositories.ProductPageableRepository;
import com.brunopbrito31.apilivros.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductPageableRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products  = productService.getAllProducts();
        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Optional<Product> productSearched = productService.getProductById(id);
        if(!productSearched.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productSearched.get());
    }

    @PostMapping
    public ResponseEntity<Product> insertProduct(@RequestBody Product product){
        product = productService.saveProduct(product);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(product);
    }
    
}
