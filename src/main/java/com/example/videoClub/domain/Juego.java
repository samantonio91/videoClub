package com.example.videoClub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_juego")
@PrimaryKeyJoinColumn(name = "juego_id")
@Getter
@Setter
@NoArgsConstructor
public class Juego extends Producto {
    private String plataforma;
    private String genero;
}
