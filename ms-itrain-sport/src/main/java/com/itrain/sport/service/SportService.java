package com.itrain.sport.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.itrain.sport.domain.Sport;
import com.itrain.sport.repository.SportRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SportService {

    private final SportRepository sportRepository;

    @Transactional
    public Set<Sport> findAllById(final Set<Long> ids) {

        return new HashSet<>(sportRepository.findAllById(ids));
    }

    @Transactional
    public Page<Sport> getAll(final Pageable pageable) {

        return sportRepository.findAll(pageable);
    }

    @Transactional
    public void deleteAllById(final Set<Long> ids) {

        sportRepository.deleteAll(sportRepository.findAllById(ids));
    }

    public Set<Sport> saveAll(final Set<Sport> sports) {

        return new HashSet<>(sportRepository.saveAll(sports));
    }

    @Transactional
    public Set<Sport> createAll(final Set<Sport> sports) {

        sports.forEach(s -> s.setId(null));

        return saveAll(sports);
    }

    @Transactional
    public Set<Sport> edit(final Set<Sport> sports) {

        final var ids = sports.stream().map(Sport::getId).collect(Collectors.toSet());

        final var currentSports = findAllById(ids);

        if (currentSports.isEmpty()) {

            return Collections.emptySet();
        }

        sports.forEach(s -> currentSports
            .stream()
            .filter(s::equals)
            .forEach(cs -> cs.fillFrom(s)));

        return saveAll(currentSports);
    }

}