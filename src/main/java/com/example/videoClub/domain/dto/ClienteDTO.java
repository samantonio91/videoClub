package com.example.videoClub.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO {
    private String documento;
    private String nombre;
    private String telefono;
    private String email;
    // Compatible con el contrato inicial; vincular alquileres es opcional.
    private List<Long> alquilerIds;
}
