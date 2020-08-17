package com.itrain.personaltrainer.service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.itrain.personaltrainer.domain.Contact;
import com.itrain.personaltrainer.repository.ContactRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service(value = "personal-trainer-contact-service")
@RequiredArgsConstructor
public class ContactService {

    private final PersonalTrainerService personalTrainerService;
    private final ContactRepository contactRepository;

    @Transactional
    public Set<Contact> add(final Long personalTrainerId, final Set<Contact> contacts) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        contacts.forEach(c -> {

            c.setId(null);

            c.addPersonalTrainer(personalTrainer);

        });

        contactRepository.saveAll(contacts);

        personalTrainerService.save(personalTrainer);

        return contacts;
    }

    @Transactional
    public Set<Contact> edit(final Long personalTrainerId, final Set<Contact> contacts) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        final var currentContacts = personalTrainer.getContacts();

        contacts.retainAll(currentContacts);

        if (contacts.isEmpty()) {

            return Collections.emptySet();
        }

        contacts.forEach(c -> currentContacts
            .stream()
            .filter(c::equals)
            .forEach(cc -> cc.fillFrom(c)));

        personalTrainerService.save(personalTrainer);

        return contacts;

    }

    @Transactional
	public Set<Contact> delete(final Long personalTrainerId, final Set<Long> contactIds) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        final var currentContacts = personalTrainer.getContacts();

        final var toDelete = currentContacts
            .stream()
            .filter(c -> contactIds.contains(c.getId()))
            .collect(Collectors.toSet());

        if (toDelete.isEmpty()) {

            return Collections.emptySet();
        }

        contactRepository.deleteAll(toDelete);

        currentContacts.removeAll(toDelete);

        personalTrainerService.save(personalTrainer);

        return toDelete;
	}

	public Set<Contact> getAll(Long personalTrainerId) {

        final var personalTrainer = personalTrainerService.findById(personalTrainerId);

        return personalTrainer.getContacts();
	}

}
