package com.itrain.client.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.itrain.client.domain.Client;
import com.itrain.client.repository.ClientRepository;

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

}