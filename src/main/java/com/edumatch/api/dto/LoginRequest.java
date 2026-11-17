package com.edumatch.api.dto;

import jakarta.validation.constraints.NotBlank;

/** Datos que llegan para iniciar sesión. */
public record LoginRequest(
        @NotBlank(message = "El correo es obligatorio") String correo,
        @NotBlank(message = "La contraseña es obligatoria") String contrasena) {
}
