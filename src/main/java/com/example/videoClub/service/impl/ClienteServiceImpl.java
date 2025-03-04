package com.example.videoClub.service.impl;

import com.example.videoClub.domain.Alquiler;
import com.example.videoClub.domain.Cliente;
import com.example.videoClub.domain.dto.ClienteDTO;
import com.example.videoClub.repository.AlquilerRepository;
import com.example.videoClub.repository.ClienteRepository;
import com.example.videoClub.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    ClienteRepository clienteRepository;
    AlquilerRepository alquilerRepository;

    @Override
    public Cliente guardarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setEmail(clienteDTO.getEmail());

        if (clienteDTO.getAlquilerIds() != null && !clienteDTO.getAlquilerIds().isEmpty()) {
            List<Alquiler> alquileres = clienteDTO.getAlquilerIds().stream()
                    .map(alquilerId -> alquilerRepository.findById(alquilerId).orElse(null))
                    .collect(Collectors.toList());
            cliente.setAlquileres(alquileres);
        }

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente obtenerClientePorId(Long clienteId){
        return clienteRepository.findById(clienteId).orElse(null);
    }

}
