package com.example.videoClub.repository;

import com.example.videoClub.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c WHERE "
            + "(:documento IS NULL OR LOWER(c.documento) LIKE LOWER(CONCAT(CONCAT('%', :documento), '%'))) "
            + "AND (:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT(CONCAT('%', :nombre), '%'))) "
            + "ORDER BY c.nombre")
    List<Cliente> buscarPorDocumentoYNombre(
            @Param("documento") String documento,
            @Param("nombre") String nombre);
}
