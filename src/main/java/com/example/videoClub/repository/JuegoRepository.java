package com.example.videoClub.repository;

import com.example.videoClub.domain.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {

}
