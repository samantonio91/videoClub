package com.example.videoClub.repository;

import com.example.videoClub.domain.Cliente;
import com.example.videoClub.domain.Juego;
import com.example.videoClub.domain.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ModeloUmlJpaTest {
    @Autowired TestEntityManager entityManager;
    @Autowired ClienteRepository clientes;
    @Autowired JuegoRepository juegos;

    @Test
    void filtrarClientesPorDocumentoYNombre() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Ana García");
        cliente.setDocumento("ABC-123");
        clientes.saveAndFlush(cliente);

        assertEquals(1, clientes.buscarPorDocumentoYNombre("ABC", "Ana").size());
        assertTrue(clientes.buscarPorDocumentoYNombre("XYZ", null).isEmpty());
    }

    @Test
    void juegoHeredaLosDatosDeProducto() {
        Juego juego = new Juego();
        juego.setCodigo(7);
        juego.setTitulo("Juego de ejemplo");
        juego.setPrecio(120.0);
        juego.setPlataforma("PC");
        juego.setGenero("Aventura");

        Juego guardado = juegos.saveAndFlush(juego);
        entityManager.clear();

        Juego recuperado = juegos.findById(guardado.getProductId()).orElseThrow();
        assertInstanceOf(Producto.class, recuperado);
        assertEquals("Juego de ejemplo", recuperado.getTitulo());
        assertEquals("PC", recuperado.getPlataforma());
    }
}
