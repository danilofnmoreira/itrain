package com.itrain.gym.service;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service(value = "gym-sport-service")
@RequiredArgsConstructor
public class SportService {

    private final GymService gymService;

    @Transactional
    public void add(final Long gymId, final Set<Long> sportIds) {

        final var gym = gymService.findById(gymId);

        gym.addParsedSports(sportIds);

        gymService.save(gym);
    }

    @Transactional
    public void edit(final Long gymId, final Set<Long> sportIds) {

        final var gym = gymService.findById(gymId);

        gym.setSports(null);

        gym.addParsedSports(sportIds);

        gymService.save(gym);
    }

    public Set<Long> getAll(Long gymId) {

        final var gym = gymService.findById(gymId);

        return gym.getParsedSports();
    }

}
