package com.example.videoClub.domain.dto;

/** Datos para registrar o actualizar un juego y sus atributos heredados de Producto. */
public record JuegoDTO(
        Integer codigo,
        String titulo,
        Double precio,
        String plataforma,
        String genero
) {
}
