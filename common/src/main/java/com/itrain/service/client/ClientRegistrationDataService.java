package com.itrain.service.client;

import com.itrain.domain.User;
import com.itrain.payload.api.v1.RegistrationData;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientRegistrationDataService {

    private ClientService clientService;

    public void update(User user, RegistrationData registrationData) {

        var client = clientService.findByUser(user);

        client.setContacts(registrationData.getContacts());
        client.setAddresses(registrationData.getAddresses());

        clientService.save(client);
    }
}