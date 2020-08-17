package com.itrain.personaltrainer.mapper;

import java.util.Objects;

import com.itrain.personaltrainer.controller.v1.model.PersonalTrainerModel;
import com.itrain.personaltrainer.domain.PersonalTrainer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonalTrainerMapper {

    public static PersonalTrainer createFrom(final PersonalTrainerModel model, final Long personalTrainerId) {

        final var personalTrainer = PersonalTrainer
            .builder()
            .id(personalTrainerId)
            .contacts(ContactMapper.createFrom(model.getContacts()))
            .addresses(AddressMapper.createFrom(model.getAddresses()))
            .biography(model.getBiography())
            .instagram(model.getInstagram())
            .build();

        personalTrainer.addParsedSports(model.getSports());

        return personalTrainer;
    }

    public static PersonalTrainer createNullSafeFrom(final PersonalTrainerModel model, final Long personalTrainerId) {

        final var nullSafeModel = Objects.requireNonNullElse(model, new PersonalTrainerModel());

        final var personalTrainer = PersonalTrainer
            .builder()
            .id(personalTrainerId)
            .contacts(ContactMapper.createNullSafeFrom(nullSafeModel.getContacts()))
            .addresses(AddressMapper.createNullSafeFrom(nullSafeModel.getAddresses()))
            .biography(nullSafeModel.getBiography())
            .instagram(nullSafeModel.getInstagram())
            .build();

        personalTrainer.addParsedSports(nullSafeModel.getSports());

        return personalTrainer;
    }
}