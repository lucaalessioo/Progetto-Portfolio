package com.makeup.portfolio.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer{
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        // Mappa la richiesta /uploads/** alla cartella disica "uploads"
        registry.addResourceHandler("uploads/**")
                .addResourceLocations("file:uploads/");
    }

}
