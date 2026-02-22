package com.makeup.portfolio.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.makeup.portfolio.DTO.WorksDTO;
import com.makeup.portfolio.mapper.WorksMapper;
import com.makeup.portfolio.model.Category;
import com.makeup.portfolio.model.Work;
import com.makeup.portfolio.repository.CategoryRepository;
import com.makeup.portfolio.repository.WorksRepository;

@Service
public class WorksService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WorksRepository worksRepository;

    @Autowired
    private WorksMapper worksMapper;

    private final String uploadDir = "uploads/";

    public List<WorksDTO> getAllWorks() {
        return worksRepository.findAll()
                .stream()
                .map(worksMapper::toDto)
                .collect(Collectors.toList());
    }

    // Transictional nel caso che qualcosa va storto evita di salvare file corrotti sul db
    @Transactional
    public WorksDTO saveWork(String title, String description, Long categoryId, MultipartFile file) {
        // 1. Controllo sicurezza: è davvero un'immagine?
        validateImage(file);

        try {
            ensureDirectoryExists();

            // 2. Generiamo un nome univoco (es: a1b2c3d4-e5f6...jpg)
            String safeFileName = generateUniqueFileName(file.getOriginalFilename());
            Path filePath = Paths.get(uploadDir).resolve(safeFileName);

            // 3. Salvataggio fisico
            Files.copy(file.getInputStream(), filePath);

            // 4. Salvataggio DB
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Categoria non trovata"));

            Work work = new Work();
            work.setTitle(title);
            work.setDescription(description);
            work.setImageUrl("/uploads/" + safeFileName);
            work.setCategories(category);

            return worksMapper.toDto(worksRepository.save(work));

        } catch (IOException e) {
            throw new RuntimeException("Errore tecnico durante il salvataggio: " + e.getMessage());
        }
    }

    @Transactional
    public WorksDTO updateWork(Long id, String title, String description, Long categoryId, MultipartFile file) {
        Work work = worksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lavoro non trovato"));

        work.setTitle(title);
        work.setDescription(description);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria non trovata"));
        work.setCategories(category);

        // Se viene caricata una nuova foto
        if (file != null && !file.isEmpty()) {
            validateImage(file);
            try {
                // A. Cancelliamo la vecchia foto dal server per non accumulare spazzatura
                deletePhysicalFile(work.getImageUrl());

                // B. Salviamo la nuova con nome univoco
                String safeFileName = generateUniqueFileName(file.getOriginalFilename());
                Path filePath = Paths.get(uploadDir).resolve(safeFileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                work.setImageUrl("/uploads/" + safeFileName);
            } catch (IOException e) {
                throw new RuntimeException("Errore durante l'aggiornamento dell'immagine");
            }
        }

        return worksMapper.toDto(worksRepository.save(work));
    }

    @Transactional
    public void deleteWork(Long id) {
        Work work = worksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impossibile eliminare: Lavoro non trovato"));

        // Prima cancelliamo il file fisico
        deletePhysicalFile(work.getImageUrl());
        
        // Poi cancelliamo dal DB
        worksRepository.delete(work);
    }


      private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File vuoto o non presente");
        }
        
        String contentType = file.getContentType();
        // Controllo che il file sia effettivamente un'immagine
        if (contentType == null || !contentType.startsWith("image/")) { 
            throw new RuntimeException("Il file deve essere un'immagine (jpg, png, ecc.)");
        }
    }

    private String generateUniqueFileName(String originalName) {
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }

    // Controlla che la cartella uploads esiste. Se non esiste la crea.
    private void ensureDirectoryExists() throws IOException {
        Path directoryPath = Paths.get(uploadDir);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
    }

    private void deletePhysicalFile(String imageUrl) {
        if (imageUrl == null) return;
        try {
            // Rimuoviamo il prefisso "/uploads/" per ottenere il nome file
            String fileName = imageUrl.replace("/uploads/", "");
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Avviso: Impossibile eliminare il file fisico " + imageUrl);
        }
    }
}
