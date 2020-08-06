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

    public static Address createFrom(final AddressModel addressModel) {

        final var model = Objects.requireNonNullElse(addressModel, new AddressModel());

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

    public static Set<Address> createFrom(final Set<AddressModel> addressesModels) {

        final var model = Objects.requireNonNullElse(addressesModels, new HashSet<AddressModel>());

        return model.stream()
            .map(AddressMapper::createFrom)
            .collect(Collectors.toSet());
    }
}