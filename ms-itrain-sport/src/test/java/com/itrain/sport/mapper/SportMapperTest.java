package com.itrain.sport.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.Set;

import com.itrain.sport.controller.v1.model.SportModel;
import com.itrain.sport.domain.Sport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "tests for sport mapper")
class SportMapperTest {

    @Nested
    @DisplayName(value = "should return a Sport domain")
    class should_return_a_Sport_domain {

        @Test
        @DisplayName(value = "with null properties, if the given SportModel is null")
        void with_null_properties_if_the_given_SportModel_is_null() {

            final var expected = new Sport();

            final SportModel sportModel = null;

            final var actual = SportMapper.createNullSafeFrom(sportModel);

            assertThat(actual, samePropertyValuesAs(expected));
        }

        @Test
        @DisplayName(value = "with the same properties values from the given SportModel if it is not null")
        void with_the_same_properties_values_from_the_given_SportModel_if_it_is_not_null() {

            final var expected = Sport
                .builder()
                .id(1L)
                .name("name")
                .build();

            final var sportModel = SportModel
                .builder()
                .id(1L)
                .name("name")
                .build();

            final var actual = SportMapper.createFrom(sportModel);

            assertThat(actual, samePropertyValuesAs(expected));
        }

    }

    @Nested
    @DisplayName(value = "should return a set of Sports domain")
    class should_return_a_set_of_Sports_domain {

        @Test
        @DisplayName(value = "without itens, if the given set of SportModel is null")
        void without_itens_if_the_given_set_of_SportModel_is_null() {

            final Set<SportModel> sportsModels = null;

            final var actual = SportMapper.createNullSafeFrom(sportsModels);

            assertThat(actual, is(emptyCollectionOf(Sport.class)));
        }

        @Test
        @DisplayName(value = "with same size and Sports with the same properties values from the given SportModel set if it is not null")
        void with_same_size_and_Sports_with_the_same_properties_values_from_the_given_SportModel_set_if_it_is_not_null() {

            final var expected = Sport
                .builder()
                .id(1L)
                .name("name")
                .build();

            final var sportsModels = Set.of(SportModel
                .builder()
                .id(1L)
                .name("name")
                .build());

            final var actual = SportMapper.createFrom(sportsModels);

            assertThat(actual, hasSize(1));
            actual.forEach(a -> assertThat(a, samePropertyValuesAs(expected)));
        }

    }
}