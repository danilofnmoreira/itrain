package com.itrain.personaltrainer.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "Address domain test")
class AddressTest {

    @Nested
    @DisplayName(value = "when two Address objects has")
    class when_two_Address_objects_has {

        @Test
        @DisplayName(value = "null ids, they are not equals")
        void null_ids_they_are_not_equals() {

            final var address1 = new Address();
            address1.setId(null);

            final var address2 = new Address();
            address2.setId(null);

            assertThat(address1, is(not(equalTo(address2))));
        }

        @Test
        @DisplayName(value = "equal ids, they are equals")
        void equal_ids_they_are_equals() {

            final var address1 = new Address();
            address1.setId(1L);

            final var address2 = new Address();
            address2.setId(1L);

            assertThat(address1, is(equalTo(address2)));
        }

    }

    @Nested
    @DisplayName(value = "after adding a personal trainer to address, they must be in a mutual relationship")
    class after_adding_a_personal_trainer_to_address_they_must_be_in_a_mutual_relationship {

        private PersonalTrainer personalTrainer;

        @BeforeEach
        void setUp() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(1L).build());
            addresses.add(Address.builder().id(2L).build());

            personalTrainer = PersonalTrainer.builder().id(1L).addresses(addresses).build();
        }

        @Test
        void test() {

            final var address = Address.builder().id(3L).build();

            address.addPersonalTrainer(personalTrainer);

            assertThat(personalTrainer.getAddresses(), hasSize(3));
            assertThat(address.getPersonalTrainer(), is(sameInstance(personalTrainer)));
            assertThat(personalTrainer.getAddresses(), hasItem(address));
        }

        @Test
        @DisplayName(value = "even if, the given personal trainer does not have a previewus set of addresses")
        void even_if_the_given_personal_trainer_does_not_have_a_previewus_set_of_addresses() {

            personalTrainer.setAddresses(null);

            final var address = Address.builder().id(4L).build();

            address.addPersonalTrainer(personalTrainer);

            assertThat(personalTrainer.getAddresses(), hasSize(1));
            assertThat(address.getPersonalTrainer(), is(sameInstance(personalTrainer)));
            assertThat(personalTrainer.getAddresses(), hasItem(address));
        }
    }

    @Test
    @DisplayName(value = "when fill an address from other, they must have the same properties values")
    void when_fill_a_address_from_other_they_must_have_the_same_properties_values() {

        final var address = Address
            .builder()
            .city("city")
            .complement("complement")
            .district("district")
            .federalUnit("federalUnit")
            .id(1L)
            .publicPlace("publicPlace")
            .zipCode("zipCode")
            .personalTrainer(new PersonalTrainer())
            .build();

        final var actual = new Address();

        actual.fillFrom(address);

        assertThat(actual, samePropertyValuesAs(address, "personalTrainer"));
    }

}