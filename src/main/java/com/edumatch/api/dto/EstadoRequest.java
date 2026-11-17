package com.edumatch.api.dto;

import com.edumatch.api.model.EstadoPostulacion;
import jakarta.validation.constraints.NotNull;

/** Datos para cambiar el estado de una postulación. */
public record EstadoRequest(
        @NotNull(message = "El estado es obligatorio") EstadoPostulacion estado) {
}
