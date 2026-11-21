package com.example.videoClub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_pelicula")
@PrimaryKeyJoinColumn(name = "pelicula_id")
@Getter
@Setter
@NoArgsConstructor
public class Pelicula extends Producto {
    private Integer duracion;
    private String idioma;
    // GeneroPelicula del UML se deja para una fase separada: no define operaciones.
}
