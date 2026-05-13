package com.makeup.portfolio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
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
        work.setDescription("Descrizione Originale");
        work.setImageUrl("/uploads/test.jpg");

        worksDto = new WorksDTO();
        worksDto.setId(1L);
        worksDto.setTitle("Trucco Sposa");

    }

    @Test
    @DisplayName("Eliminazione lavoro esistente")
    void testDeleteWork() {

        when(workRepository.findById(1L)).thenReturn(Optional.of((work)));

        //WHEN
        worksService.deleteWork(1L);

        //THEN
        verify(workRepository, times(1)).delete(work);

    }

    @Test
    void testGetAllWorks() {

        //GIVEN
        when(workRepository.findAll()).thenReturn(List.of(work));
        when(worksMapper.toDto(work)).thenReturn(worksDto);

        //WHEN
        List<WorksDTO> result = worksService.getAllWorks();

        //THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Trucco Sposa", result.get(0).getTitle());
        verify(workRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Salvataggio lavoro con successo")
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
    @DisplayName("Deve lanciare eccezione se il file non è un'immagine")
    void saveWork_InvalidFileType_ThrowsException() {
        //GIVEN
        MockMultipartFile file = new MockMultipartFile(
        "file", 
        "test.pdf", 
        "application/pdf", // <-- Questo farà fallire validateImage
        "contenuto finto".getBytes()
    );

        RuntimeException exception = assertThrows(RuntimeException.class, () ->  worksService.saveWork("Titolo", "Descrizione",1L, file));

        assertEquals("Il file deve essere un'immagine (jpg, png, ecc.)", exception.getMessage());
        verify(workRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lanciare eccezione se il file non è un'immagine")
    void saveWork_CategoryNotFound_ThrowsException() {
        //GIVEN
       MockMultipartFile file = new MockMultipartFile(
            "file", "test.jpg", "image/jpeg", "foto".getBytes()
        );

        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        //WHEN THEN
        assertThrows(RuntimeException.class, () -> {
            worksService.saveWork("Titolo", "Descrizione", 99L, file);
    });

        verify(workRepository, never()).save(any());
    }

    @Test
    @DisplayName("Aggiornamento lavoro con nuova immagine")
    void testUpdateWork_With_File() {
        //GIVEN
        MockMultipartFile newFile = new MockMultipartFile(
            "file", "nuova.jpg", "image/jpeg", "nuovo contenuto".getBytes());

            // Moking: cerco il lavoro e la categoria esistenti
            when(workRepository.findById(1L)).thenReturn(Optional.of(work));
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(workRepository.save(any(Work.class))).thenReturn(work);

            // Preparo un DTO di risposta aggiornato
            WorksDTO updateDto = new WorksDTO();
            updateDto.setId(1L);
            updateDto.setTitle("Titolo Aggiornato");
            updateDto.setDescription("Nuova Descrizione");
            when(worksMapper.toDto(any(Work.class))).thenReturn(updateDto);

            //WHEN
            WorksDTO result = worksService.updateWork(1L, "Titolo Aggiornato", "Nuova Descrizione", 1L, newFile);


            //WHEN
            assertNotNull(result);
            assertEquals("Titolo Aggiornato", result.getTitle());
            assertEquals("Nuova Descrizione", result.getDescription());

            // Verifico che i setter sia stati chiamati sull oggetto mock
            assertEquals("Titolo Aggiornato", work.getTitle());
            verify(workRepository, times(1)).save(work);
            verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Aggiornamento lavoro senza cambiare immagine")
    void updateWork_WithoutFile() {
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(workRepository.save(any(Work.class))).thenReturn(work);

        WorksDTO updateDto = new WorksDTO();
        updateDto.setTitle("Titolo Solo Testo");
        when(worksMapper.toDto(any(Work.class))).thenReturn(updateDto);

        //WHEN - passo null come file
        WorksDTO result = worksService.updateWork(1L, "Titolo Solo Testo", "Descrizione", 1L,null);

        //THEN
        assertNotNull(result);
        assertEquals("Titolo Solo Testo", result.getTitle());
        assertEquals("/uploads/test.jpg", work.getImageUrl());

        verify(workRepository, times(1)).save(work);
    }

    @Test
    @DisplayName("Aggiornamento fallito: lavoro non trovato")
    void updateWork_NotFound_ThrowsException() {

        //GIVEN
        when(workRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN THEN
        assertThrows(RuntimeException.class, () -> worksService.updateWork(99L, "Titolo", "Descrizione", 1L, null));

        verify(workRepository, never()).save(any());

    }
}
