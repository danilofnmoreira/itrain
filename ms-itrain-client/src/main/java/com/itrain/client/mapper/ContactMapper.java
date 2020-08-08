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

    public static Contact createFrom(final ContactModel model) {

        return Contact
            .builder()
            .id(model.getId())
            .name(model.getName())
            .email(model.getEmail())
            .phone(model.getPhone())
            .whatsapp(model.getWhatsapp())
            .build();
    }

    public static Set<Contact> createFrom(final Set<ContactModel> models) {

        return models
            .stream()
            .filter(c -> !Objects.isNull(c))
            .map(ContactMapper::createFrom)
            .collect(Collectors.toCollection(HashSet::new));
    }

    public static Contact createNullSafeFrom(final ContactModel model) {

        final var nullSafeModel = Objects.requireNonNullElse(model, new ContactModel());

        return createFrom(nullSafeModel);
    }

    public static Set<Contact> createNullSafeFrom(final Set<ContactModel> models) {

        final var nullSafeModels = Objects.requireNonNullElse(models, new HashSet<ContactModel>());

        return createFrom(nullSafeModels);
    }

}