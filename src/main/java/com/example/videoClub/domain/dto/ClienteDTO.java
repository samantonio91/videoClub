package com.example.videoClub.domain.dto;

import java.util.List;

public class ClienteDTO {
    private String nombre;
    private String telefono;
    private String email;
    private List<Long> alquilerIds;

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public List<Long> getAlquilerIds() {
        return alquilerIds;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", alquilerIds=" + alquilerIds +
                '}';
    }
}
