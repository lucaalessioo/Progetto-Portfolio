package com.makeup.portfolio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.makeup.portfolio.DTO.CategoryDTO;
import com.makeup.portfolio.mapper.CategoryMapper;
import com.makeup.portfolio.model.Category;
import com.makeup.portfolio.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CategoryMapper categoryMapper;
    

public List<CategoryDTO> getAllCategories() {
    return categoryRepository.findAll()
            .stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toList());
}

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria non trovata con id: " + id));
    }
}
