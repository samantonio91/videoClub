package com.example.videoClub.service;

import com.example.videoClub.domain.Cliente;
import com.example.videoClub.domain.Alquiler;
import com.example.videoClub.domain.dto.ClienteDTO;
import com.example.videoClub.repository.AlquilerRepository;
import com.example.videoClub.repository.ClienteRepository;
import com.example.videoClub.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {
    @Mock ClienteRepository clientes;
    @Mock AlquilerRepository alquileres;
    @InjectMocks ClienteServiceImpl servicio;

    private ClienteDTO datos;

    @BeforeEach
    void preparar() {
        datos = new ClienteDTO();
        datos.setDocumento("DOC-123");
        datos.setNombre("Ana García");
        datos.setTelefono("555123");
        datos.setEmail("ana@example.com");
    }

    @Test
    void agregarClienteGuardaCamposDelUml() {
        when(clientes.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));
        Cliente creado = servicio.agregarCliente(datos);
        assertEquals("Ana García", creado.getNombre());
        assertEquals("DOC-123", creado.getDocumento());
        verify(clientes).save(any(Cliente.class));
    }

    @Test
    void modificarClienteActualizaRegistroExistente() {
        Cliente actual = new Cliente();
        actual.setClienteId(5L);
        actual.setNombre("Nombre anterior");
        when(clientes.findById(5L)).thenReturn(Optional.of(actual));
        when(clientes.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        Cliente modificado = servicio.modificarCliente(5L, datos);

        assertEquals("Ana García", modificado.getNombre());
        assertEquals(5L, modificado.getClienteId());
    }

    @Test
    void eliminarClienteSinAlquileres() {
        when(clientes.existsById(5L)).thenReturn(true);
        servicio.eliminarCliente(5L);
        verify(clientes).deleteById(5L);
    }

    @Test
    void eliminarClienteConAlquileresDevuelveConflicto() {
        when(clientes.existsById(5L)).thenReturn(true);
        when(alquileres.existsByCliente_ClienteId(5L)).thenReturn(true);
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> servicio.eliminarCliente(5L));
        assertEquals(HttpStatus.CONFLICT, error.getStatusCode());
        verify(clientes, never()).deleteById(any());
    }

    @Test
    void mostrarClientesNormalizaFiltros() {
        when(clientes.buscarPorDocumentoYNombre(null, "Ana")).thenReturn(List.of(new Cliente()));
        assertEquals(1, servicio.mostrarClientes(" ", "  Ana  ").size());
    }

    @Test
    void buscarClienteInexistenteDevuelve404() {
        when(clientes.findById(99L)).thenReturn(Optional.empty());
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> servicio.buscarCliente(99L));
        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());
    }

    @Test
    void altaSinNombreDevuelve400() {
        datos.setNombre(" ");
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> servicio.agregarCliente(datos));
        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
        verifyNoInteractions(clientes);
    }

    @Test
    void alquilerInexistenteRevierteAlta() {
        datos.setAlquilerIds(List.of(10L));
        when(clientes.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));
        when(alquileres.findAllById(any())).thenReturn(List.of());
        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> servicio.agregarCliente(datos));
        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
    }
}
