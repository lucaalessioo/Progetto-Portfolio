package com.makeup.portfolio.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.makeup.portfolio.DTO.WorksDTO;


import com.makeup.portfolio.service.WorksService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController // Indica che questa classe restituisce dati (JSON) e non pagine HTML
@RequestMapping("/api/works") // L'URL base per tutte le chiamate a questo controller
@CrossOrigin(origins = "http://localhost:5173")
public class WorksController {

@Autowired
    private WorksService worksService;

    @DeleteMapping("/{id}")
    public void deleteWork(@PathVariable Long id) {
    worksService.deleteWork(id);
}

    @GetMapping
    public List<WorksDTO> getAllWorks() {
        return worksService.getAllWorks();
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public WorksDTO createWorks(
                @RequestParam("title") String title,
                @RequestParam("description") String description,
                @RequestParam("categoryId") Long categoryId,
                @RequestParam("file") MultipartFile file) {
                    
                    return worksService.saveWork(title, description, categoryId, file);
                }
    
}


