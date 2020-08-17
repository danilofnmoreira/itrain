package com.itrain.sport.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "Sport domain test")
class SportTest {

    @Nested
    @DisplayName(value = "when two Sport objects has")
    class when_two_Sport_objects_has {

        @Test
        @DisplayName(value = "null ids, they are not equals")
        void null_ids_they_are_not_equals() {

            final var sport1 = new Sport();
            sport1.setId(null);

            final var sport2 = new Sport();
            sport2.setId(null);

            assertThat(sport1, is(not(equalTo(sport2))));
        }

        @Test
        @DisplayName(value = "equal ids, they are equals")
        void equal_ids_they_are_equals() {

            final var sport1 = new Sport();
            sport1.setId(1L);

            final var sport2 = new Sport();
            sport2.setId(1L);

            assertThat(sport1, is(equalTo(sport2)));
        }

    }

    @Test
    @DisplayName(value = "when fill a sport from other, they must have the same properties values")
    void when_fill_a_sport_from_other_they_must_have_the_same_properties_values() {

        final var sport = Sport
            .builder()
            .id(1L)
            .name("name")
            .build();

        final var actual = new Sport();

        actual.fillFrom(sport);

        assertThat(actual, samePropertyValuesAs(sport));
    }
}