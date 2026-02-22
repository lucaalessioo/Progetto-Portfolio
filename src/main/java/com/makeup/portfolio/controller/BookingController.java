package com.makeup.portfolio.controller;

import com.makeup.portfolio.DTO.BookingRequest;
import com.makeup.portfolio.model.Utente;
import com.makeup.portfolio.repository.UtenteRepository;
import com.makeup.portfolio.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UtenteRepository utenteRepository;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request, Principal principal) {
        // Recuperiamo l'utente autenticato dal database tramite il nome nel Token
        Utente utenteLoggato = utenteRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utente non autenticato"));

        // Usiamo l'email REALE dell'utente salvata nel DB, ignorando quella del frontend
        bookingService.sendBookingEmail(
                utenteLoggato.getUsername(),
                utenteLoggato.getEmail(), 
                request.getServiceTitle(),
                request.getDate());

        return ResponseEntity.ok().body("Prenotazione inviata con successo!");
    }
}
