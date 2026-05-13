package com.makeup.portfolio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.makeup.portfolio.DTO.CategoryDTO;
import com.makeup.portfolio.mapper.CategoryMapper;
import com.makeup.portfolio.model.Category;
import com.makeup.portfolio.repository.CategoryRepository;



@ExtendWith(MockitoExtension.class) 
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryMapper categoryMapper;

    @InjectMocks
    CategoryService categoryService;


    @Test
    @DisplayName("Dovrebbe restituire una lista di CategoryDTO")
    void getAllCategories_ShouldReturnDtoList() {

        //GIVEN
        Category cat = new Category();
        cat.setId(1L);
        CategoryDTO dto = new CategoryDTO();
        dto.setId(1L);

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(cat));
        when(categoryMapper.toDto(any(Category.class))).thenReturn(dto);

        //WHEN
        List<CategoryDTO> result = categoryService.getAllCategories();
        
        //THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(categoryRepository, times(1)).findAll();
        

    }


    @Test
    void testGetCategoryById() {

    }

    @Test
    void testSaveCategory() {

    }
}
