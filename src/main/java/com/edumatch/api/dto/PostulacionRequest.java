package com.edumatch.api.dto;

import jakarta.validation.constraints.NotNull;

/** Datos para postularse a una oportunidad. */
public record PostulacionRequest(
        @NotNull(message = "El usuario es obligatorio") Long usuarioId,
        @NotNull(message = "La oportunidad es obligatoria") Long oportunidadId) {
}
