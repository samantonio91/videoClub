package com.example.videoClub.controller;

import com.example.videoClub.domain.dto.ClienteDTO;
import com.example.videoClub.domain.dto.ClienteResponse;
import com.example.videoClub.domain.dto.JuegoDTO;
import com.example.videoClub.domain.dto.JuegoResponse;
import com.example.videoClub.service.ClienteService;
import com.example.videoClub.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UML operations implemented as REST endpoints.
 * Request validation and response mapping belong to the HTTP boundary.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VideoClubController {
    private final ClienteService clienteService;
    private final JuegoService juegoService;

    @PostMapping("/clientes")
    public ResponseEntity<ClienteResponse> agregarCliente(@Valid @RequestBody ClienteDTO datos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClienteResponse.from(clienteService.agregarCliente(datos)));
    }

    @PutMapping("/clientes/{codigo}")
    public ResponseEntity<ClienteResponse> modificarCliente(
            @PathVariable Long codigo, @Valid @RequestBody ClienteDTO datos) {
        return ResponseEntity.ok(ClienteResponse.from(clienteService.modificarCliente(codigo, datos)));
    }

    @DeleteMapping("/clientes/{codigo}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long codigo) {
        clienteService.eliminarCliente(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteResponse>> mostrarClientes(
            @RequestParam(required = false) String documento,
            @RequestParam(required = false) String nombre) {
        return ResponseEntity.ok(clienteService.mostrarClientes(documento, nombre).stream()
                .map(ClienteResponse::from).toList());
    }

    @GetMapping("/clientes/{codigo}")
    public ResponseEntity<ClienteResponse> buscarCliente(@PathVariable Long codigo) {
        return ResponseEntity.ok(ClienteResponse.from(clienteService.buscarCliente(codigo)));
    }

    @PostMapping("/juegos")
    public ResponseEntity<JuegoResponse> agregarJuego(@Valid @RequestBody JuegoDTO datos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JuegoResponse.from(juegoService.agregarJuego(datos)));
    }

    @PutMapping("/juegos/{codigo}")
    public ResponseEntity<JuegoResponse> modificarJuego(
            @PathVariable Long codigo, @Valid @RequestBody JuegoDTO datos) {
        return ResponseEntity.ok(JuegoResponse.from(juegoService.modificarJuego(codigo, datos)));
    }

    // Legacy routes retained; response JSON is now a stable DTO representation.
    @PostMapping("/save-cliente")
    public ResponseEntity<ClienteResponse> saveCliente(@Valid @RequestBody ClienteDTO datos) {
        return ResponseEntity.ok(ClienteResponse.from(clienteService.agregarCliente(datos)));
    }

    @GetMapping("/get-client")
    public ResponseEntity<ClienteResponse> getCliente(
            @RequestParam(name = "cliente_id") Long clienteId) {
        return ResponseEntity.ok(ClienteResponse.from(clienteService.buscarCliente(clienteId)));
    }
}
