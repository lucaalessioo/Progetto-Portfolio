package com.makeup.portfolio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.makeup.portfolio.DTO.WorksDTO;
import com.makeup.portfolio.mapper.WorksMapper;
import com.makeup.portfolio.model.Category;
import com.makeup.portfolio.model.Work;
import com.makeup.portfolio.repository.CategoryRepository;
import com.makeup.portfolio.repository.WorksRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test per WorksService")
public class WorksServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private WorksRepository workRepository;

    @Mock
    private WorksMapper worksMapper;

    @InjectMocks
    private WorksService worksService;

    private Category category;
    private Work work;
    private WorksDTO worksDto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);

        work = new Work();
        work.setId(1L);
        work.setTitle("Trucco sposa");
        work.setImageUrl("/uploads/test.jpg");

        worksDto = new WorksDTO();
        worksDto.setId(1L);
        worksDto.setTitle("Trucco Sposa");

    }

    @Test
    void testDeleteWork() {

    }

    @Test
    void testGetAllWorks() {

        

    }

    @Test
    void testSaveWork() throws IOException {

        //GIVEN
        MockMultipartFile file = new MockMultipartFile(
            "file","test.jpg","image/jpeg","contenuto immagine".getBytes());

            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(workRepository.save(any(Work.class))).thenReturn(work);
            when(worksMapper.toDto(any(Work.class))).thenReturn(worksDto);

        //WHEN
        WorksDTO result = worksService.saveWork("Titolo", "Descrizione",1L, file);

        //THEN
        assertNotNull(result);
        assertEquals("Trucco Sposa", result.getTitle());
        verify(workRepository, times(1)).save(any(Work.class));
        verify(categoryRepository, times(1)).findById(1L);

    }

    @Test
    void testUpdateWork() {

    }
}
