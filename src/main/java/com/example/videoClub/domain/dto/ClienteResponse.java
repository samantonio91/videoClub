package com.example.videoClub.domain.dto;

import com.example.videoClub.domain.Cliente;

/** Public REST response; JPA relationships and Hibernate proxies are not serialized. */
public record ClienteResponse(
        Long id,
        String documento,
        String nombre,
        String telefono,
        String email
) {
    public static ClienteResponse from(Cliente customer) {
        return new ClienteResponse(
                customer.getClienteId(),
                customer.getDocumento(),
                customer.getNombre(),
                customer.getTelefono(),
                customer.getEmail());
    }
}
