package com.example.videoClub.service.impl;

import com.example.videoClub.domain.Alquiler;
import com.example.videoClub.repository.AlquilerRepository;
import com.example.videoClub.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlquierServiceImpl implements AlquilerService {

    @Autowired
    AlquilerRepository alquilerRepository;

    @Override
    @Transactional
    public void createAlquiler(Alquiler alquiler) {
        alquilerRepository.save(alquiler);
    }
}
