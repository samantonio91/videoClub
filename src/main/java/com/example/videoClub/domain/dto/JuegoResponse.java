package com.example.videoClub.domain.dto;

import com.example.videoClub.domain.Juego;

/** Public REST response independent from JPA inheritance/proxy serialization. */
public record JuegoResponse(
        Long id,
        Integer codigo,
        String titulo,
        Double precio,
        String plataforma,
        String genero
) {
    public static JuegoResponse from(Juego game) {
        return new JuegoResponse(
                game.getProductId(),
                game.getCodigo(),
                game.getTitulo(),
                game.getPrecio(),
                game.getPlataforma(),
                game.getGenero());
    }
}
