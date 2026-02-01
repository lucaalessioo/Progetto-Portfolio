package com.makeup.portfolio.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.makeup.portfolio.service.JwtService;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Usiamo UserDetailsService (l'interfaccia standard di Spring) 
    // che punterà alla tua implementazione CustomUserDetailsService
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
) throws ServletException, IOException {

    // AGGIUNTA: Se la richiesta è per l'autenticazione, passa oltre immediatamente
    // Questo evita che il filtro faccia calcoli inutili durante il login
    if (request.getServletPath().contains("/api/auth")) {
        filterChain.doFilter(request, response);
        return;
    }

    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String username;

    // 1. Controllo rapido: se non c'è il Bearer token, vai avanti nella catena
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    // 2. Estrazione token
    jwt = authHeader.substring(7);
    try {
        username = jwtService.extractUsername(jwt);

        // 3. Se abbiamo uno username e l'utente non è già autenticato
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4. Validazione finale del token
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 5. Autenticazione completata
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    } catch (Exception e) {
        // Log dell'errore (usa il logger della classe)
        logger.error("Impossibile impostare l'autenticazione utente: " + e.getMessage());
    }

    filterChain.doFilter(request, response);
}
}

    
