package com.example.videoClub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tab_alquiler")
@Getter
@Setter
@NoArgsConstructor
public class Alquiler implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alquilerId;

    @Column(name = "fecha_alquiler")
    private LocalDate fechaAlquiler;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    private Boolean alquilado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
