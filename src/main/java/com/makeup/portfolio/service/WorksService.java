package com.makeup.portfolio.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    // Cartella di salvataggio immaggini trucchi
    private final String uploadDir = "uploads/";

    // Richiamo i lavori dal db
    // Li converto in dto
    // E li metto in una lista
    public List<WorksDTO> getAllWorks() {
        return worksRepository.findAll()
                .stream()
                .map(worksMapper::toDto)
                .collect(Collectors.toList());
    }

    public WorksDTO saveWork(String title, String description, Long categoryId, MultipartFile file) {
        try {
            // Se non esiste la cartella la crea
            Path directoryPath = Paths.get(uploadDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // 2. Definiamo il percorso completo del file (cartella + nome file)
            String fileName = file.getOriginalFilename();
            Path filePath = directoryPath.resolve(fileName);

            // 3. Copiamo fisicamente il file nella cartella
            // StandardCopyOption.REPLACE_EXISTING serve a sovrascrivere se il file esiste
            // già
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Categoria non trovata"));

            // 4. Creiamo l'entità da salvare nel DB
            Work work = new Work();
            work.setTitle(title);
            work.setDescription(description);
            work.setImageUrl("/uploads/" + fileName); // Questo è l'indirizzo che userà il frontend
            work.setCategories(category);

            // 5. Salviamo e convertiamo in DTO per il controller
            Work savedWork = worksRepository.save(work);
            return worksMapper.toDto(savedWork);

        } catch (IOException e) {
            // Se c'è un errore (cartella protetta, disco pieno, ecc.) lanciamo un'eccezione
            throw new RuntimeException("Impossibile salvare il file: " + e.getMessage());
        }
    }

    // Metodo UPDATE
    public WorksDTO updateWork(Long id, String title, String description, Long categoryId, MultipartFile file) {
        Work work = worksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lavoro non trovato"));

        work.setTitle(title);
        work.setDescription(description);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria non trovata"));
        work.setCategories(category);

        // Se l'utente ha caricato una nuova foto, la sostituiamo
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir).resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                work.setImageUrl("/uploads/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Errore nel salvataggio della nuova immagine");
            }
        }

        Work updatedWork = worksRepository.save(work);
        return worksMapper.toDto(updatedWork);
    }

    public void deleteWork(Long id) {
        worksRepository.deleteById(id);
        // Nota: per ora cancelliamo solo il record sul DB.
        // Per pulire anche il file fisico servirebbe Files.delete(), ma partiamo dal
        // DB.
    }

}
