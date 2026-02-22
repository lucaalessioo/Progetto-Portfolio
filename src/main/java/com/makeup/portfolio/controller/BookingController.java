package com.makeup.portfolio.controller;

<<<<<<< HEAD
import com.makeup.portfolio.DTO.BookingRequest;
import com.makeup.portfolio.model.Utente;
import com.makeup.portfolio.repository.UtenteRepository;
import com.makeup.portfolio.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makeup.portfolio.DTO.BookingRequest;
import com.makeup.portfolio.service.BookingService;
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    @Autowired
    private BookingService bookingService;

<<<<<<< HEAD
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
=======
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        // servizio email
        bookingService.sendBookingEmail(
                request.getCustomerName(),
                request.getCustomerEmail(),
                request.getServiceTitle(),
                request.getDate());
        return ResponseEntity.ok().body("Prenotazione inviata!");
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b
    }
}
