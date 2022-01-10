package com.brunopbrito31.apilivros.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Category;
import com.brunopbrito31.apilivros.models.entities.Product;
import com.brunopbrito31.apilivros.services.CategoryService;
import com.brunopbrito31.apilivros.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory(){
        List<Category> categories = categoryService.getAllCategories();
        if(categories.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(!category.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(category.get());
    }

    @PostMapping()
    public ResponseEntity<Category> insertCategory(@RequestBody Category category){
        category = categoryService.insertCategory(category);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(category.getId()).toUri();

        return ResponseEntity.created(uri).body(category);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Product> insertProductInCategory(@PathVariable Long id, @RequestBody Product product){

        Optional<Category> categorySearched = categoryService.getCategoryById(id);

        if(product.getId() != null){
            Optional<Product> productSearched = productService.getProductById(product.getId());
            product = productSearched.get();
        }

        if(!categorySearched.isPresent()){
            return ResponseEntity.badRequest().build();
        }

        product.setCategory(categorySearched.get());
        product = productService.saveProduct(product);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
        Optional<Category> categorySearched = categoryService.getCategoryById(id);
        if(!categorySearched.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        Category oldCategory = categorySearched.get();
        oldCategory.setName(category.getName());
        oldCategory = categoryService.insertCategory(oldCategory);
        return ResponseEntity.ok().body(oldCategory);
    }


}
