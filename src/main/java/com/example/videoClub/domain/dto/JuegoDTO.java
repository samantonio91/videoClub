package com.example.videoClub.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/** Validated request fields for games and inherited Product attributes. */
public record JuegoDTO(
        @PositiveOrZero Integer codigo,
        @NotBlank @Size(max = 200) String titulo,
        @NotNull @DecimalMin("0.0") Double precio,
        @NotBlank @Size(max = 80) String plataforma,
        @NotBlank @Size(max = 80) String genero
) {
}
