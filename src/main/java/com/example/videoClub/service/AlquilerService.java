package com.example.videoClub.service;

import com.example.videoClub.domain.Alquiler;
import org.springframework.stereotype.Service;

@Service
public interface AlquilerService {

    public void createAlquiler(Alquiler alquiler);

}
