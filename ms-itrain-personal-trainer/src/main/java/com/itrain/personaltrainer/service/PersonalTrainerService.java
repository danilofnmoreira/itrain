package com.itrain.personaltrainer.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import com.itrain.personaltrainer.domain.PersonalTrainer;
import com.itrain.personaltrainer.repository.PersonalTrainerRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonalTrainerService {

    private final PersonalTrainerRepository personalTrainerRepository;

    public PersonalTrainer save(PersonalTrainer personalTrainer) {

        var now = LocalDateTime.now();

        personalTrainer.setRegisteredAt(now);
        personalTrainer.setUpdatedAt(now);

        return personalTrainerRepository.save(personalTrainer);
    }

    public PersonalTrainer findById(Long id) {

        return personalTrainerRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Personal Trainer, %s, not found.", id)));
    }

}