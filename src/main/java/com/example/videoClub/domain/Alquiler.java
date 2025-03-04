package com.example.videoClub.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="tab_alquiler")
public class Alquiler implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long alquilerId;

        @JsonProperty("fecha_alquiler")
        @Column(name = "fecha_alquiler")
        private String fechaAlquiler;

        @JsonProperty("fecha_entrega")
        @Column(name = "fecha_entrega")
        private String fechaEntrega;

        private Boolean alquilado;

        @ManyToOne
        @JoinColumn(name = "cliente_id")
        private Cliente cliente;

}
