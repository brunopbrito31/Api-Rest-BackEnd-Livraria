package com.brunopbrito31.apilivros.services;

import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.Category;
import com.brunopbrito31.apilivros.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Optional<Category> getCategoryById(Long id){
        Optional<Category> categorySearched = categoryRepository.findById(id);
        return categorySearched;
    }

    public Category insertCategory(Category category){
        category = categoryRepository.save(category);
        return category;
    }
}
