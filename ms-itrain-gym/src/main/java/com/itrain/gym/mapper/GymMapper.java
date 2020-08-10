package com.itrain.gym.mapper;

import java.util.Objects;

import com.itrain.gym.controller.v1.model.GymModel;
import com.itrain.gym.domain.Gym;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GymMapper {

    public static Gym createFrom(final GymModel model, final Long gymId) {

        final var gym = Gym
            .builder()
            .id(gymId)
            .contacts(ContactMapper.createFrom(model.getContacts()))
            .addresses(AddressMapper.createFrom(model.getAddresses()))
            .biography(model.getBiography())
            .instagram(model.getInstagram())
            .build();

        gym.addParsedSports(model.getSports());

        return gym;
    }

    public static Gym createNullSafeFrom(final GymModel model, final Long gymId) {

        final var nullSafeModel = Objects.requireNonNullElse(model, new GymModel());

        final var gym = Gym
            .builder()
            .id(gymId)
            .contacts(ContactMapper.createNullSafeFrom(nullSafeModel.getContacts()))
            .addresses(AddressMapper.createNullSafeFrom(nullSafeModel.getAddresses()))
            .biography(nullSafeModel.getBiography())
            .instagram(nullSafeModel.getInstagram())
            .build();

        gym.addParsedSports(nullSafeModel.getSports());

        return gym;
    }
}