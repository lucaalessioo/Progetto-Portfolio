package com.makeup.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Usiamo il percorso assoluto per evitare errori di puntamento su Windows
        // Nota: usiamo tre slash dopo 'file:' e lo slash finale
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/Users/lucaa/Documents/GitHub/Progetto-Portfolio/uploads/")
                .setCachePeriod(0);
    }
}