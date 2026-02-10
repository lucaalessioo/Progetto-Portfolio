package com.makeup.portfolio.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.makeup.portfolio.model.Utente;
import com.makeup.portfolio.repository.UtenteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    // Spring inietterà automaticamente il tuo UtenteRepository
    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cerchiamo l'utente nel DB (restituisce un Optional<Utente>)
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con username: " + username));

        // Trasformiamo il tuo modello "Utente" nel modello "UserDetails" che Spring
        // capisce
        return User.builder()
                .username(utente.getUsername())
                .password(utente.getPassword()) // Attenzione: deve essere già criptata nel DB
                .authorities(utente.getRole())
                .build();
    }
}