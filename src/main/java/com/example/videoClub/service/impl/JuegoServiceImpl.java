package com.example.videoClub.service.impl;

import com.example.videoClub.domain.Juego;
import com.example.videoClub.repository.JuegoRepository;
import com.example.videoClub.service.JuegoService;

public class JuegoServiceImpl implements JuegoService {
    JuegoRepository juegoRepository;

    @Override
    public void createJuego(Juego juego) {
        juegoRepository.save(juego);
    }
}
