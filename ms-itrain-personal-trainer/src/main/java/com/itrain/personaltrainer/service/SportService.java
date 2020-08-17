package com.itrain.personaltrainer.service;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service(value = "personal-trainer-sport-service")
@RequiredArgsConstructor
public class SportService {

    private final PersonalTrainerService personalTrainerService;

    @Transactional
    public void add(final Long personalTrainerId, final Set<Long> sportIds) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        personalTrainer.addParsedSports(sportIds);

        personalTrainerService.save(personalTrainer);
    }

    @Transactional
    public void edit(final Long personalTrainerId, final Set<Long> sportIds) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        personalTrainer.setSports(null);

        personalTrainer.addParsedSports(sportIds);

        personalTrainerService.save(personalTrainer);
    }

    public Set<Long> getAll(Long personalTrainerId) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        return personalTrainer.getParsedSports();
    }

}
