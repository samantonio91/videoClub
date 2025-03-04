package com.example.videoClub.service;

import com.example.videoClub.domain.Cliente;
import com.example.videoClub.domain.dto.ClienteDTO;

public interface ClienteService {
    public Cliente guardarCliente(ClienteDTO clienteDTO);
    public Cliente obtenerClientePorId(Long clienteId);
}
