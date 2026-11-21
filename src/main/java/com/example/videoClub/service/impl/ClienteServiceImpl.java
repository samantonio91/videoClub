package com.example.videoClub.service.impl;

import com.example.videoClub.domain.Alquiler;
import com.example.videoClub.domain.Cliente;
import com.example.videoClub.domain.dto.ClienteDTO;
import com.example.videoClub.repository.AlquilerRepository;
import com.example.videoClub.repository.ClienteRepository;
import com.example.videoClub.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;
    private final AlquilerRepository alquilerRepository;

    @Override
    @Transactional
    public Cliente agregarCliente(ClienteDTO datos) {
        validarCliente(datos);
        Cliente cliente = new Cliente();
        aplicarDatos(cliente, datos);
        cliente = clienteRepository.save(cliente);
        asociarAlquileresSiSeIndican(cliente, datos);
        return cliente;
    }

    @Override
    @Transactional
    public Cliente modificarCliente(Long codigo, ClienteDTO datos) {
        validarCliente(datos);
        Cliente cliente = buscarCliente(codigo);
        aplicarDatos(cliente, datos);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long codigo) {
        if (!clienteRepository.existsById(codigo)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
        if (alquilerRepository.existsByCliente_ClienteId(codigo)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar un cliente que tiene alquileres asociados");
        }
        clienteRepository.deleteById(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> mostrarClientes(String documento, String nombre) {
        return clienteRepository.buscarPorDocumentoYNombre(limpiarFiltro(documento), limpiarFiltro(nombre));
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarCliente(Long codigo) {
        return clienteRepository.findById(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    private void validarCliente(ClienteDTO datos) {
        if (datos == null || datos.getNombre() == null || datos.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del cliente es obligatorio");
        }
    }

    private void aplicarDatos(Cliente cliente, ClienteDTO datos) {
        cliente.setDocumento(limpiarFiltro(datos.getDocumento()));
        cliente.setNombre(datos.getNombre().trim());
        cliente.setTelefono(limpiarFiltro(datos.getTelefono()));
        cliente.setEmail(limpiarFiltro(datos.getEmail()));
    }

    private String limpiarFiltro(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }

    private void asociarAlquileresSiSeIndican(Cliente cliente, ClienteDTO datos) {
        if (datos.getAlquilerIds() == null || datos.getAlquilerIds().isEmpty()) {
            return;
        }
        Set<Long> ids = new HashSet<>(datos.getAlquilerIds());
        if (ids.contains(null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los IDs de alquiler deben ser válidos");
        }
        List<Alquiler> alquileres = alquilerRepository.findAllById(ids);
        if (alquileres.size() != ids.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hay alquileres inexistentes");
        }
        if (alquileres.stream().anyMatch(a -> a.getCliente() != null)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Hay alquileres asignados a otro cliente");
        }
        alquileres.forEach(a -> a.setCliente(cliente));
        alquilerRepository.saveAll(alquileres);
        cliente.getAlquileres().addAll(alquileres);
    }
}
