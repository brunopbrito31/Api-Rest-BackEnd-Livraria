package com.brunopbrito31.apilivros.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Product;
import com.brunopbrito31.apilivros.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // @GetMapping
    // public ResponseEntity<List<Product>> getProducts() {
    //     List<Product> products  = productService.getAllProducts();
    //     if(products.isEmpty()) {
    //         return ResponseEntity.noContent().build();
    //     }
    //     return ResponseEntity.ok().body(products);
    // }

    @GetMapping
    public ResponseEntity<List<Product>> getProductsWithPagination(
        @RequestParam(defaultValue = "0") Integer pageNo,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam(defaultValue = "id") String sortBy)
    {
        List<Product> list = productService.getAllProductsPageable(pageNo, pageSize, sortBy);

        return new ResponseEntity<List<Product>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Optional<Product> productSearched = productService.getProductById(id);
        return productSearched.isPresent() ? 
            ResponseEntity.ok().body(productSearched.get()) : 
            ResponseEntity.notFound().build();
    }

    @GetMapping("/description")
    public ResponseEntity<List<Product>> getProductByDescription(@RequestParam String description){
        List<Product> productSearched = productService.getProductByTitle(description);
        return productSearched.isEmpty() ? 
            ResponseEntity.noContent().build() : 
            ResponseEntity.ok().body(productSearched);
    }

    @GetMapping("/total")
    public Integer getTotalProduct(){
        return productService.getTotalProducts();
    }

    @PostMapping
    public ResponseEntity<Product> insertProduct(@RequestBody Product product){
        product = productService.saveProduct(product);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(product);
    }
    
}
