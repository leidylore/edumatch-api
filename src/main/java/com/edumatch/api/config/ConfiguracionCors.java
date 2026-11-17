package com.edumatch.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración general: permite que el front-end en React (puerto 5173)
 * consuma la API y define el cifrador de contraseñas.
 */
@Configuration
public class ConfiguracionCors implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registro) {
        registro.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    /** Cifrador BCrypt: las contraseñas nunca se guardan en texto plano. */
    @Bean
    public BCryptPasswordEncoder cifradorContrasena() {
        return new BCryptPasswordEncoder();
    }
}
