package com.itrain.gym.service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.itrain.common.exception.DuplicateEntityException;
import com.itrain.gym.domain.Address;
import com.itrain.gym.domain.Contact;
import com.itrain.gym.domain.Gym;
import com.itrain.gym.repository.GymRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service(value = "gym-service")
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    public Gym save(final Gym gym) {

        return gymRepository.save(gym);
    }

    public Gym findById(final Long id) {

        return findOptionalById(id).orElseThrow(() -> new NoSuchElementException(String.format("Gym, %s, not found.", id)));
    }

    public Optional<Gym> findOptionalById(final Long id) {

        return gymRepository.findById(id);
    }

    @Transactional
    public Gym create(final Gym gym) {

        if (gymRepository.existsById(gym.getId())) {
            throw new DuplicateEntityException(String.format("Gym, %s, already exists.", gym.getId()));
        }

        final var addresses = Objects.requireNonNullElse(gym.getAddresses(), new HashSet<Address>());
        addresses.forEach(a -> a.setId(null));

        final var contacts = Objects.requireNonNullElse(gym.getContacts(), new HashSet<Contact>());
        contacts.forEach(c -> c.setId(null));

        gym.addAddresses(addresses);

        gym.addContacts(contacts);

        return save(gym);
    }

}