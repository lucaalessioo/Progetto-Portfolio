package com.makeup.portfolio.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makeup.portfolio.DTO.CategoryDTO;
import com.makeup.portfolio.model.Category;
import com.makeup.portfolio.service.CategoryService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

  @GetMapping
public List<CategoryDTO> getAllCategories() {
    return categoryService.getAllCategories();
}

    
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        
        return categoryService.saveCategory(category);
    }
    
}
