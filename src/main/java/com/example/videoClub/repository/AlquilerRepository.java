package com.example.videoClub.repository;

import com.example.videoClub.domain.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerRepository extends JpaRepository <Alquiler, Long> {

    @Query("select a from Alquiler a where a.alquilado = ?1")
    Alquiler getAlquilerByAlquilado(String alquilado);

}
