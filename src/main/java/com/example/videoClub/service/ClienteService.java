package com.example.videoClub.service;

import com.example.videoClub.domain.Cliente;
import com.example.videoClub.domain.dto.ClienteDTO;

import java.util.List;

/** Operaciones de clientes definidas para VideoClub en el UML. */
public interface ClienteService {
    Cliente agregarCliente(ClienteDTO datos);
    Cliente modificarCliente(Long codigo, ClienteDTO datos);
    void eliminarCliente(Long codigo);
    List<Cliente> mostrarClientes(String documento, String nombre);
    Cliente buscarCliente(Long codigo);
}
