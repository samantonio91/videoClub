package com.example.videoClub.service.impl;

import com.example.videoClub.domain.Juego;
import com.example.videoClub.domain.dto.JuegoDTO;
import com.example.videoClub.repository.JuegoRepository;
import com.example.videoClub.service.JuegoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class JuegoServiceImpl implements JuegoService {
    private final JuegoRepository juegoRepository;

    @Override
    @Transactional
    public Juego agregarJuego(JuegoDTO datos) {
        validar(datos);
        Juego juego = new Juego();
        aplicarDatos(juego, datos);
        return juegoRepository.save(juego);
    }

    @Override
    @Transactional
    public Juego modificarJuego(Long codigo, JuegoDTO datos) {
        validar(datos);
        Juego juego = juegoRepository.findById(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Juego no encontrado"));
        aplicarDatos(juego, datos);
        return juegoRepository.save(juego);
    }

    private void validar(JuegoDTO datos) {
        if (datos == null || datos.titulo() == null || datos.titulo().isBlank()
                || datos.precio() == null || datos.precio() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Título y precio válido son obligatorios");
        }
    }

    private void aplicarDatos(Juego juego, JuegoDTO datos) {
        juego.setCodigo(datos.codigo());
        juego.setTitulo(datos.titulo().trim());
        juego.setPrecio(datos.precio());
        juego.setPlataforma(datos.plataforma());
        juego.setGenero(datos.genero());
    }
}
