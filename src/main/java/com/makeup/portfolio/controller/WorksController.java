package com.makeup.portfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makeup.portfolio.model.Works;
import com.makeup.portfolio.repository.WorksRepository;

    
@RestController // Indica che questa classe restituisce dati (JSON) e non pagine HTML
@RequestMapping("/api/lavori") // L'URL base per tutte le chiamate a questo controller
@CrossOrigin(origins = "*") // Permette al Front-end di chiamare il Back-end senza blocchi di sicurezza (CORS)
public class WorksController {

    @Autowired
    private WorksRepository worksRepository;

    // Metodo per ottenere tutti i lavori (La galleria del portfolio)
    @GetMapping
    public List<Works> getAllWorks() {
        return worksRepository.findAll();
    }

    // Metodo per ottenere i dettagli di un singolo lavoro
    @GetMapping("/{id}")
    public Works getLavoroById(@PathVariable Long id) {
        return worksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lavoro non trovato con id: " + id));
    }
}


