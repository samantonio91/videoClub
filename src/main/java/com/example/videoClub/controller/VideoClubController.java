package com.example.videoClub.controller;

import com.example.videoClub.domain.Cliente;
import com.example.videoClub.domain.Juego;
import com.example.videoClub.domain.dto.ClienteDTO;
import com.example.videoClub.domain.dto.JuegoDTO;
import com.example.videoClub.service.ClienteService;
import com.example.videoClub.service.JuegoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VideoClubController {
    private final ClienteService clienteService;
    private final JuegoService juegoService;

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> agregarCliente(@RequestBody ClienteDTO datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.agregarCliente(datos));
    }

    @PutMapping("/clientes/{codigo}")
    public ResponseEntity<Cliente> modificarCliente(@PathVariable Long codigo, @RequestBody ClienteDTO datos) {
        return ResponseEntity.ok(clienteService.modificarCliente(codigo, datos));
    }

    @DeleteMapping("/clientes/{codigo}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long codigo) {
        clienteService.eliminarCliente(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> mostrarClientes(
            @RequestParam(required = false) String documento,
            @RequestParam(required = false) String nombre) {
        return ResponseEntity.ok(clienteService.mostrarClientes(documento, nombre));
    }

    @GetMapping("/clientes/{codigo}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable Long codigo) {
        return ResponseEntity.ok(clienteService.buscarCliente(codigo));
    }

    @PostMapping("/juegos")
    public ResponseEntity<Juego> agregarJuego(@RequestBody JuegoDTO datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(juegoService.agregarJuego(datos));
    }

    @PutMapping("/juegos/{codigo}")
    public ResponseEntity<Juego> modificarJuego(@PathVariable Long codigo, @RequestBody JuegoDTO datos) {
        return ResponseEntity.ok(juegoService.modificarJuego(codigo, datos));
    }

    // Rutas originales preservadas por compatibilidad.
    @PostMapping("/save-cliente")
    public ResponseEntity<Cliente> saveCliente(@RequestBody ClienteDTO datos) {
        return ResponseEntity.ok(clienteService.agregarCliente(datos));
    }

    @GetMapping("/get-client")
    public ResponseEntity<Cliente> getCliente(@RequestParam(name = "cliente_id") Long clienteId) {
        return ResponseEntity.ok(clienteService.buscarCliente(clienteId));
    }
}
