package com.itrain.personaltrainer.service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.itrain.common.exception.DuplicateEntityException;
import com.itrain.personaltrainer.domain.Address;
import com.itrain.personaltrainer.domain.Contact;
import com.itrain.personaltrainer.domain.PersonalTrainer;
import com.itrain.personaltrainer.repository.PersonalTrainerRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service(value = "personal-trainer-service")
@RequiredArgsConstructor
public class PersonalTrainerService {

    private final PersonalTrainerRepository personalTrainerRepository;

    public PersonalTrainer save(final PersonalTrainer personalTrainer) {

        final var now = ZonedDateTime.now();

        personalTrainer.setRegisteredAt(now);
        personalTrainer.setUpdatedAt(now);

        return personalTrainerRepository.save(personalTrainer);
    }

    public PersonalTrainer findById(final Long id) {

        return findOptionalById(id).orElseThrow(() -> new NoSuchElementException(String.format("Personal trainer, %s, not found.", id)));
    }

    public Optional<PersonalTrainer> findOptionalById(final Long id) {

        return personalTrainerRepository.findById(id);
    }

    @Transactional
    public PersonalTrainer create(final PersonalTrainer personalTrainer) {

        if (personalTrainerRepository.existsById(personalTrainer.getId())) {
            throw new DuplicateEntityException(String.format("Personal trainer, %s, already exists.", personalTrainer.getId()));
        }

        final var addresses = Objects.requireNonNullElse(personalTrainer.getAddresses(), new HashSet<Address>());
        addresses.forEach(a -> a.setId(null));

        final var contacts = Objects.requireNonNullElse(personalTrainer.getContacts(), new HashSet<Contact>());
        contacts.forEach(c -> c.setId(null));

        personalTrainer.addAddresses(addresses);

        personalTrainer.addContacts(contacts);

        return save(personalTrainer);
    }

}