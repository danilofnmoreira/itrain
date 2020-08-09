package com.itrain.gym.service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.itrain.gym.domain.Contact;
import com.itrain.gym.repository.ContactRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service(value = "gym-contact-service")
@RequiredArgsConstructor
public class ContactService {

    private final GymService gymService;
    private final ContactRepository contactRepository;

    @Transactional
    public Set<Contact> add(final Long gymId, final Set<Contact> contacts) {

        final var gym = gymService.findById(gymId);

        contacts.forEach(c -> {

            c.setId(null);

            c.addGym(gym);

        });

        contactRepository.saveAll(contacts);

        gymService.save(gym);

        return contacts;
    }

    @Transactional
    public Set<Contact> edit(final Long gymId, final Set<Contact> contacts) {

        final var gym = gymService.findById(gymId);

        final var currentContacts = gym.getContacts();

        contacts.retainAll(currentContacts);

        if (contacts.isEmpty()) {

            return Collections.emptySet();
        }

        contacts.forEach(c -> currentContacts
            .stream()
            .filter(c::equals)
            .forEach(cc -> cc.fillFrom(c)));

        gymService.save(gym);

        return contacts;

    }

    @Transactional
	public Set<Contact> delete(final Long gymId, final Set<Long> contactIds) {

        final var gym = gymService.findById(gymId);

        final var currentContacts = gym.getContacts();

        final var toDelete = currentContacts
            .stream()
            .filter(c -> contactIds.contains(c.getId()))
            .collect(Collectors.toSet());

        if (toDelete.isEmpty()) {

            return Collections.emptySet();
        }

        contactRepository.deleteAll(toDelete);

        currentContacts.removeAll(toDelete);

        gymService.save(gym);

        return toDelete;
	}

	public Set<Contact> getAll(Long gymId) {

        final var gym = gymService.findById(gymId);

        return gym.getContacts();
	}

}
