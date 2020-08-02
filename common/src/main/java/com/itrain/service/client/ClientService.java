package com.itrain.service.client;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import com.itrain.domain.Client;
import com.itrain.domain.User;
import com.itrain.repository.ClientRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client save(Client client) {

        var now = LocalDateTime.now();

        client.setRegisteredAt(now);
        client.setUpdatedAt(now);

        return clientRepository.save(client);
    }

    public Client findByUser(User user) {

        return clientRepository.findById(user).orElseThrow(() -> new NoSuchElementException(String.format("Client, %s, not found.", user.getUsername())));
    }

}