package com.itrain.client.mapper;

import java.util.Objects;

import com.itrain.client.controller.v1.model.ClientModel;
import com.itrain.client.domain.Client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client createFrom(final ClientModel clientModel, final Long clientId) {

        final var model = Objects.requireNonNullElse(clientModel, new ClientModel());

        return Client.builder()
            .id(clientId)
            .contacts(ContactMapper.createFrom(model.getContacts()))
            .addresses(AddressMapper.createFrom(model.getAddresses()))
            .build();
    }
}