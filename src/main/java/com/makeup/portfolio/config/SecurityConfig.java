package com.makeup.portfolio.config;

import com.makeup.portfolio.config.JwtAuthFilter;

import org.springframework.security.config.Customizer;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Fondamentale per criptare le password
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

<<<<<<< HEAD
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // 1. Permessi Pubblici SPECIFICI (Solo login e registrazione)
            .requestMatchers("/api/auth/login", "/api/auth/register").permitAll() 
            
            // 2. Permessi GET Pubblici
            .requestMatchers(HttpMethod.GET, "/api/works/**", "/api/category/**", "/uploads/**").permitAll()
            
            // 3. Permessi SOLO ADMIN
            .requestMatchers("/api/category/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/works/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/works/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/works/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
            
            // 4. L'aggiornamento profilo richiede autenticazione (qualsiasi ruolo)
            .requestMatchers("/api/auth/update-profile").authenticated()
            
            // Tutto il resto richiede login
            .anyRequest().authenticated())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
=======
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // Obbligatorio disabilitarlo per API REST con JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Niente
                                                                                                              // sessioni
                                                                                                              // su
                                                                                                              // server
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/api/bookings", "/api/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/works/**", "/api/category/**", "/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/works/**", "/api/category/**", "/uploads/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/works/**", "/api/category/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .anyRequest().authenticated())
                // DICIAMO A SPRING DI USARE IL FILTRO JWT PRIMA DI QUELLO STANDARD
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
>>>>>>> f0aa7f454de28a2a1c123a77bcfe6316e6033d7b

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Il tuo React
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
