package com.makeup.portfolio.controller;

import com.makeup.portfolio.DTO.LoginRequest;
import com.makeup.portfolio.DTO.LoginResponse;
import com.makeup.portfolio.service.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Per permettere a React di chiamare questo endpoint
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, 
                          UserDetailsService userDetailsService, 
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        // 1. Spring Security controlla se username e password sono corretti
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. Se arriviamo qui, l'autenticazione è riuscita. Carichiamo l'utente
        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

        // 3. Generiamo il token per lui
        final String jwt = jwtService.generateToken(user);

        return new LoginResponse(jwt);
    }
}
