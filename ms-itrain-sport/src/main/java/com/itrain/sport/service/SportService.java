package com.itrain.sport.service;

import java.util.NoSuchElementException;

import com.itrain.sport.domain.Sport;
import com.itrain.sport.repository.SportRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SportService {

    private final SportRepository sportRepository;

    public Sport save(Sport sport) {

        return sportRepository.save(sport);
    }

    public Sport findById(Long id) {

        return sportRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Sport, %s, not found.", id)));
    }

}