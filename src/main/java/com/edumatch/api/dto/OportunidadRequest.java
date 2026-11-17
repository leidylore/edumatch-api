package com.edumatch.api.dto;

import com.edumatch.api.model.TipoOportunidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/** Datos para crear o actualizar una oportunidad. */
public record OportunidadRequest(
        @NotBlank(message = "El título es obligatorio") String titulo,
        String descripcion,
        @NotNull(message = "El tipo es obligatorio (BECA, CURSO o PROGRAMA_INTERCAMBIO)") TipoOportunidad tipo,
        LocalDate fechaLimite,
        @NotNull(message = "La institución es obligatoria") Long institucionId) {
}
