package com.example.videoClub.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/** Validated request payload for customer creation and replacement. */
@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO {
    @Size(max = 80)
    private String documento;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120)
    private String nombre;

    @Size(max = 30)
    private String telefono;

    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 254)
    private String email;

    // Legacy input supported for backwards compatibility; not returned to API consumers.
    private List<@NotNull @Positive Long> alquilerIds;
}
