package com.itrain.client.service;

import java.util.Collections;
import java.util.Set;

import javax.transaction.Transactional;

import com.itrain.client.domain.Contact;
import com.itrain.client.repository.ContactRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ClientService clientService;
    private final ContactRepository contactRepository;

    @Transactional
    public Set<Contact> add(final Long clientId, final Set<Contact> contacts) {

        final var client = clientService.findById(clientId);

        contacts.forEach(c -> {

            c.setId(null);

            c.addClient(client);

        });

        contactRepository.saveAll(contacts);

        clientService.save(client);

        return contacts;
    }

    @Transactional
    public Set<Contact> edit(final Long clientId, final Set<Contact> contacts) {

        final var client = clientService.findById(clientId);

        final var currentContacts = client.getContacts();

        contacts.retainAll(currentContacts);

        if (contacts.isEmpty()) {

            return Collections.emptySet();
        }

        contacts.forEach(c -> currentContacts
            .stream()
            .filter(c::equals)
            .forEach(cc -> cc.fillFrom(c)));

        clientService.save(client);

        return contacts;

    }

}
