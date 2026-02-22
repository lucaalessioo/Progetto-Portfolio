package com.makeup.portfolio.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
<<<<<<< HEAD
import java.util.UUID;
=======
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.transaction.annotation.Transactional;
=======
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b
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

<<<<<<< HEAD
    private final String uploadDir = "uploads/";

=======
    // Cartella di salvataggio immaggini trucchi
    private final String uploadDir = "uploads/";

    // Richiamo i lavori dal db
    // Li converto in dto
    // E li metto in una lista
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b
    public List<WorksDTO> getAllWorks() {
        return worksRepository.findAll()
                .stream()
                .map(worksMapper::toDto)
                .collect(Collectors.toList());
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b
    public WorksDTO updateWork(Long id, String title, String description, Long categoryId, MultipartFile file) {
        Work work = worksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lavoro non trovato"));

        work.setTitle(title);
        work.setDescription(description);
<<<<<<< HEAD
=======

>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria non trovata"));
        work.setCategories(category);

<<<<<<< HEAD
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

    // --- METODI PRIVATI DI UTILITÀ ---

    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) throw new RuntimeException("File vuoto");
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {   // controllo che il file è effettivamente una immaggine e nient altro
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
=======
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
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b
