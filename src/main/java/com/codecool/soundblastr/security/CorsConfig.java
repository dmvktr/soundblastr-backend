package com.codecool.soundblastr.security;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/auth/**").allowedOrigins("http://localhost:3000");
                registry.addMapping("/event/**").allowedOrigins("http://localhost:3000");
                registry.addMapping("/band/**").allowedOrigins("http://localhost:3000");
                registry.addMapping("/venue/**").allowedOrigins("http://localhost:3000");
            }
        };
    }
}
