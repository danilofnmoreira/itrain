package com.itrain.client.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import com.itrain.client.domain.Address;
import com.itrain.client.domain.Client;
import com.itrain.client.domain.Contact;
import com.itrain.client.repository.ClientRepository;
import com.itrain.common.exception.DuplicateEntityException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client save(final Client client) {

        final var now = LocalDateTime.now();

        client.setRegisteredAt(now);
        client.setUpdatedAt(now);

        return clientRepository.save(client);
    }

    public Client findById(final Long id) {

        return findOptionalById(id).orElseThrow(() -> new NoSuchElementException(String.format("Client, %s, not found.", id)));
    }

    public Optional<Client> findOptionalById(final Long id) {

        return clientRepository.findById(id);
    }

    public Client create(final Client client) {

        if (clientRepository.existsById(client.getId())) {
            throw new DuplicateEntityException(String.format("Client, %s, already exists.", client.getId()));
        }

        final var addresses = Objects.requireNonNullElse(client.getAddresses(), new HashSet<Address>());
        addresses.forEach(a -> a.setId(null));

        final var contacts = Objects.requireNonNullElse(client.getContacts(), new HashSet<Contact>());
        contacts.forEach(c -> c.setId(null));

        client.addAddresses(addresses);

        client.addContacts(contacts);

        return save(client);
    }

}