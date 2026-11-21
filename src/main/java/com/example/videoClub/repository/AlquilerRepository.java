package com.example.videoClub.repository;

import com.example.videoClub.domain.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
    List<Alquiler> findByAlquilado(Boolean alquilado);
    boolean existsByCliente_ClienteId(Long clienteId);
}
