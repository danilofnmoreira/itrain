package com.itrain.client.mapper;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.itrain.client.controller.v1.model.AddressModel;
import com.itrain.client.domain.Address;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapper {

    public static Address createFrom(final AddressModel model) {

        return Address
            .builder()
            .zipCode(model.getZipCode())
            .publicPlace(model.getPublicPlace())
            .complement(model.getComplement())
            .district(model.getDistrict())
            .city(model.getCity())
            .federalUnit(model.getFederalUnit())
            .build();
    }

    public static Set<Address> createFrom(final Set<AddressModel> models) {

        return models
            .stream()
            .filter(a -> !Objects.isNull(a))
            .map(AddressMapper::createFrom)
            .collect(Collectors.toCollection(HashSet::new));
    }

    public static Address createNullSafeFrom(final AddressModel model) {

        final var nullSafeModel = Objects.requireNonNullElse(model, new AddressModel());

        return createFrom(nullSafeModel);
    }

    public static Set<Address> createNullSafeFrom(final Set<AddressModel> models) {

        final var nullSafeModels = Objects.requireNonNullElse(models, new HashSet<AddressModel>());

        return createFrom(nullSafeModels);
    }

}