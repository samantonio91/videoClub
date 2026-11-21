package com.example.videoClub.service;

import com.example.videoClub.domain.Juego;
import com.example.videoClub.domain.dto.JuegoDTO;

/** Operaciones de juegos definidas para VideoClub en el UML. */
public interface JuegoService {
    Juego agregarJuego(JuegoDTO datos);
    Juego modificarJuego(Long codigo, JuegoDTO datos);
}
