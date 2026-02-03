package com.makeup.portfolio.DTO;

import lombok.Data;

@Data 
public class WorksDTO {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    
 
    private CategoryDTO categories; 
}
