package com.itrain.client.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.transaction.Transactional;

import com.itrain.client.domain.Contact;
import com.itrain.client.exception.ContactOwnershipConflictException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ClientService clientService;

    @Transactional
    public Set<Contact> add(final Long clientId, final Set<Contact> contacts) {

        final var client = clientService.findById(clientId);

        contacts.forEach(c -> c.setId(null));

        client.addContacts(contacts);

        return clientService.save(client).getContacts();
    }

    @Transactional
    public Set<Contact> edit(final Long clientId, final Set<Contact> contacts) {

        final var client = clientService.findById(clientId);

        final var currentContacts = Objects.requireNonNullElse(client.getContacts(), new HashSet<Contact>());

        if (contacts.retainAll(currentContacts)) {

            if (contacts.isEmpty()) {

                throw new ContactOwnershipConflictException("None of the given contacts belong to the client.");
            }

            currentContacts.removeAll(contacts);
            client.addContacts(contacts);
        }

        return clientService.save(client).getContacts();
    }

}
