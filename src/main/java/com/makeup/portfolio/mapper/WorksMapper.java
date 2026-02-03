package com.makeup.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.makeup.portfolio.DTO.CategoryDTO;
import com.makeup.portfolio.DTO.WorksDTO;
import com.makeup.portfolio.model.Work;

@Component
public class WorksMapper {

public WorksDTO toDto(Work work) {
    WorksDTO dto = new WorksDTO();
    dto.setId(work.getId());
    dto.setTitle(work.getTitle());
    dto.setDescription(work.getDescription());
    dto.setImageUrl(work.getImageUrl());
    
    // FONDAMENTALE: Se la categoria esiste, trasformala in DTO
    if (work.getCategories() != null) {
        CategoryDTO catDto = new CategoryDTO();
        catDto.setId(work.getCategories().getId());
        catDto.setName(work.getCategories().getName());
        dto.setCategories(catDto); 
    }
    
    return dto;
}
}
