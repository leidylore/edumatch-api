package com.edumatch.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la API de EduMatch.
 * Al ejecutarla, Spring Boot arranca el servidor en el puerto 8080.
 */
@SpringBootApplication
public class EdumatchApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdumatchApiApplication.class, args);
    }
}
