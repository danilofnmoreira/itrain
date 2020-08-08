package com.itrain.client.mapper;

import java.util.Objects;

import com.itrain.client.controller.v1.model.ClientModel;
import com.itrain.client.domain.Client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client createFrom(final ClientModel model, final Long clientId) {

        return Client
            .builder()
            .id(clientId)
            .contacts(ContactMapper.createFrom(model.getContacts()))
            .addresses(AddressMapper.createFrom(model.getAddresses()))
            .build();
    }

    public static Client createNullSafeFrom(final ClientModel model, final Long clientId) {

        final var nullSafeModel = Objects.requireNonNullElse(model, new ClientModel());

        return Client
            .builder()
            .id(clientId)
            .contacts(ContactMapper.createNullSafeFrom(nullSafeModel.getContacts()))
            .addresses(AddressMapper.createNullSafeFrom(nullSafeModel.getAddresses()))
            .build();
    }
}