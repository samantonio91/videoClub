package com.example.videoClub.controller;

import com.example.videoClub.domain.Alquiler;
import com.example.videoClub.domain.Cliente;
import com.example.videoClub.domain.dto.ClienteDTO;
import com.example.videoClub.service.ClienteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class VideoClubController {

    @Autowired
    private final ClienteService clienteService;


    @PostMapping(value="/save-cliente")
    public ResponseEntity<Cliente> saveCliente(@RequestBody ClienteDTO clienteRB) {
        log.info("Cliente guardado: {}", clienteRB.toString());
        Cliente clienteGuardado = clienteService.guardarCliente(clienteRB);
        return ResponseEntity.ok(clienteGuardado);
    }

    @GetMapping(value="/get-client")
    public ResponseEntity<Cliente> getCliente(@RequestParam(name = "cliente_id") Long clienteId) {
        Cliente cliente = clienteService.obtenerClientePorId(clienteId);
        return ResponseEntity.ok(cliente);
    }
}
