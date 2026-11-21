package com.example.videoClub.service;

import com.example.videoClub.domain.Juego;
import com.example.videoClub.domain.dto.JuegoDTO;
import com.example.videoClub.repository.JuegoRepository;
import com.example.videoClub.service.impl.JuegoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JuegoServiceImplTest {
    @Mock JuegoRepository juegos;
    @InjectMocks JuegoServiceImpl servicio;

    @Test
    void agregarJuegoIncluyeCamposHeredadosDeProducto() {
        when(juegos.save(any(Juego.class))).thenAnswer(inv -> inv.getArgument(0));
        Juego creado = servicio.agregarJuego(new JuegoDTO(12, "Aventura", 199.0, "PC", "Acción"));
        assertEquals("Aventura", creado.getTitulo());
        assertEquals(199.0, creado.getPrecio());
        assertEquals("PC", creado.getPlataforma());
    }

    @Test
    void modificarJuegoActualizaEntidadExistente() {
        Juego existente = new Juego();
        existente.setProductId(5L);
        when(juegos.findById(5L)).thenReturn(Optional.of(existente));
        when(juegos.save(any(Juego.class))).thenAnswer(inv -> inv.getArgument(0));
        Juego actualizado = servicio.modificarJuego(5L, new JuegoDTO(45, "Nuevo", 250.0, "PS5", "RPG"));
        assertEquals(5L, actualizado.getProductId());
        assertEquals(45, actualizado.getCodigo());
        assertEquals("RPG", actualizado.getGenero());
    }

    @Test
    void modificarJuegoInexistenteDevuelve404() {
        when(juegos.findById(55L)).thenReturn(Optional.empty());
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> servicio.modificarJuego(55L, new JuegoDTO(1, "Juego", 99.0, "PC", "Aventura")));
        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());
    }

    @Test
    void precioNegativoDevuelve400() {
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> servicio.agregarJuego(new JuegoDTO(1, "Juego", -1.0, "PC", "Acción")));
        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
        verifyNoInteractions(juegos);
    }
}
