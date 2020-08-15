package com.itrain.sport.mapper;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.itrain.sport.controller.v1.model.SportModel;
import com.itrain.sport.domain.Sport;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SportMapper {

    public static Sport createFrom(final SportModel model) {

        return Sport
            .builder()
            .id(model.getId())
            .name(model.getName())
            .build();
    }

    public static Set<Sport> createFrom(final Set<SportModel> models) {

        return models
            .stream()
            .filter(c -> !Objects.isNull(c))
            .map(SportMapper::createFrom)
            .collect(Collectors.toCollection(HashSet::new));
    }

    public static Sport createNullSafeFrom(final SportModel model) {

        final var nullSafeModel = Objects.requireNonNullElse(model, new SportModel());

        return createFrom(nullSafeModel);
    }

    public static Set<Sport> createNullSafeFrom(final Set<SportModel> models) {

        final var nullSafeModels = Objects.requireNonNullElse(models, new HashSet<SportModel>());

        return createFrom(nullSafeModels);
    }

}