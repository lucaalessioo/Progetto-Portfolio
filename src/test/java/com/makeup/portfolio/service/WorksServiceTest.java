package com.makeup.portfolio.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        worksDto = new WorksDTO();
        worksDto.setTitle("Trucco Sposa");

    }

    @Test
    void testDeleteWork() {

    }

    @Test
    void testGetAllWorks() {

    }

    @Test
    void testSaveWork() {

    }

    @Test
    void testUpdateWork() {

    }
}
