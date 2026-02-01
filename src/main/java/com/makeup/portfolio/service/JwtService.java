package com.makeup.portfolio.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key signingKey;

    @PostConstruct
    public void init() {
        // Inizializziamo la chiave una volta sola all'avvio
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetails user) {
        // Trasformiamo le Authorities in una lista di stringhe semplici (es. ["ROLE_ADMIN"])
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("roles", roles) // Passiamo stringhe, non oggetti
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
