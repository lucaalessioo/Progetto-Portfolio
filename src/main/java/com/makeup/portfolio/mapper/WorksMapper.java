package com.makeup.portfolio.mapper;

import org.springframework.stereotype.Component;

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
        if (work.getCategories() != null) {
            dto.setCategoryId(work.getCategories().getId());
            dto.setCategoryName(work.getCategories().getName());
        }
        return dto;
    }
}
