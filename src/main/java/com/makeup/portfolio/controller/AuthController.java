package com.makeup.portfolio.controller;

import com.makeup.portfolio.DTO.LoginRequest;
import com.makeup.portfolio.DTO.LoginResponse;
import com.makeup.portfolio.model.Utente;
import com.makeup.portfolio.repository.UtenteRepository;
import com.makeup.portfolio.service.JwtService;

<<<<<<< HEAD
import java.security.Principal;

=======
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Per permettere a React di chiamare questo endpoint
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UtenteRepository utenteRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService,
            UtenteRepository utenteRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.utenteRepository = utenteRepository;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        // 1. Spring Security controlla se username e password sono corretti
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Utente utente = utenteRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // 2. Se arriviamo qui, l'autenticazione è riuscita. Carichiamo l'utente
        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

        // 3. Generiamo il token per lui
        final String jwt = jwtService.generateToken(user);

        return new LoginResponse(jwt, utente.getRole(), utente.getEmail());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
<<<<<<< HEAD
        // Verifichiamo se lo username esiste già per evitare errori SQL
=======
        // Verifichiamo se lo username esiste già per evitare errori SQL 
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b
        if (utenteRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Errore: Username già in uso!");
        }

        // Creiamo il nuovo utente
        Utente nuovoUtente = new Utente();
        nuovoUtente.setUsername(request.getUsername());

        // CRIPTIAMO LA PASSWORD (usando il passwordEncoder definito nella config)
        nuovoUtente.setPassword(passwordEncoder.encode(request.getPassword()));

        nuovoUtente.setEmail(request.getEmail());

        // ASSEGNIAMO IL RUOLO GUEST DI DEFAULT
        nuovoUtente.setRole("ROLE_GUEST");

        utenteRepository.save(nuovoUtente);

        return ResponseEntity.ok("Registrazione avvenuta con successo! Ora puoi accedere.");
    }
<<<<<<< HEAD

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody LoginRequest request, Principal principal) {

        if (principal == null) {
        return ResponseEntity.status(401).body("Errore: Sessione scaduta o non valida.");
    }
        // principal.getName() ci dà lo username dell'utente loggato estratto dal JWT
        Utente utente = utenteRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Aggiornamento dati
        utente.setUsername(request.getUsername());
        utente.setEmail(request.getEmail());

        // Se la password è stata inviata (non vuota), la criptiamo e la salviamo
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            utente.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        utenteRepository.save(utente);
        return ResponseEntity.ok("Profilo aggiornato con successo!");
    }
=======
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b
}
