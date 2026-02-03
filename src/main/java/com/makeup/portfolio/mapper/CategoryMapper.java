package com.makeup.portfolio.mapper;

import org.springframework.stereotype.Component;
import com.makeup.portfolio.DTO.CategoryDTO;
import com.makeup.portfolio.model.Category;

@Component
public class CategoryMapper {
    public CategoryDTO toDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }
}
