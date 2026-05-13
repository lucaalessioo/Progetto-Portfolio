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

    public CategoryDTO saveCategory(CategoryDTO dto) {
    // Converto il DTO in Entity per poterlo salvare
    Category entity = categoryMapper.toEntity(dto);
    
    // Salvo l'entity sul database
    Category savedEntity = categoryRepository.save(entity);
    
    // Riconverto l'entity salvata (che ora ha un ID) in DTO
    return categoryMapper.toDto(savedEntity);
}

    public CategoryDTO getCategoryById(Long id) {
    return categoryRepository.findById(id)
            .map(categoryMapper::toDto) // Converto l entita in dto
            .orElseThrow(() -> new RuntimeException("Categoria non trovata con id: " + id));
}
}
