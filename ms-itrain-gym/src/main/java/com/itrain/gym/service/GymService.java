package com.itrain.gym.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import com.itrain.gym.domain.Gym;
import com.itrain.gym.repository.GymRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    public Gym save(Gym gym) {

        var now = LocalDateTime.now();

        gym.setRegisteredAt(now);
        gym.setUpdatedAt(now);

        return gymRepository.save(gym);
    }

    public Gym findById(Long id) {

        return gymRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Gym, %s, not found.", id)));
    }

}