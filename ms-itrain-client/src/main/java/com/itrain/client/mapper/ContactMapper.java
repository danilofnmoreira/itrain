package com.itrain.client.mapper;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.itrain.client.controller.v1.model.ContactModel;
import com.itrain.client.domain.Contact;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactMapper {

    public static Contact createFrom(final ContactModel contactModel) {

        final var model = Objects.requireNonNullElse(contactModel, new ContactModel());

        return Contact.builder()
            .id(model.getId())
            .name(model.getName())
            .email(model.getEmail())
            .phone(model.getPhone())
            .whatsapp(model.getWhatsapp())
            .build();
    }

    public static Set<Contact> createFrom(final Set<ContactModel> contactsModels) {

        final var models = Objects.requireNonNullElse(contactsModels, new HashSet<ContactModel>());

        return models.stream()
            .map(ContactMapper::createFrom)
            .collect(Collectors.toSet());
    }
}