package com.itrain.gym.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.Collections;
import java.util.Set;

import com.itrain.gym.controller.v1.model.AddressModel;
import com.itrain.gym.controller.v1.model.GymModel;
import com.itrain.gym.controller.v1.model.ContactModel;
import com.itrain.gym.domain.Address;
import com.itrain.gym.domain.Gym;
import com.itrain.gym.domain.Contact;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "tests for gym mapper")
class GymMapperTest {

    @Nested
    @DisplayName(value = "should return a Gym domain")
    class should_return_an_Gym_domain {

        @Test
        @DisplayName(value = "with null properties, if the given GymModel is null")
        void with_null_properties_if_the_given_GymModel_is_null() {

            final var expected = Gym
                .builder()
                .id(1L)
                .addresses(Collections.emptySet())
                .contacts(Collections.emptySet())
                .build();

            final GymModel gymModel = null;

            final Long gymId = 1L;

            final var actual = GymMapper.createNullSafeFrom(gymModel, gymId);

            assertThat(actual, samePropertyValuesAs(expected));
        }

        @Test
        @DisplayName(value = "with the same properties values from the given GymModel if it is not null")
        void with_the_same_properties_values_from_the_given_GymModel_if_it_is_not_null() {

            final var expectedAddress = Address
                .builder()
                .city("city")
                .complement("complement")
                .district("district")
                .federalUnit("federalUnit")
                .publicPlace("publicPlace")
                .zipCode("zipCode")
                .build();

            final var expectedContact = Contact
                .builder()
                .email("email")
                .name("name")
                .phone("phone")
                .whatsapp(true)
                .build();

            final var expected = Gym
                .builder()
                .id(1L)
                .addresses(Set.of(expectedAddress))
                .contacts(Set.of(expectedContact))
                .build();

            final var contactModel = Set.of(ContactModel
                .builder()
                .email("email")
                .name("name")
                .phone("phone")
                .whatsapp(true)
                .build());

            final var addressModel = Set.of(AddressModel
                .builder()
                .city("city")
                .complement("complement")
                .district("district")
                .federalUnit("federalUnit")
                .publicPlace("publicPlace")
                .zipCode("zipCode")
                .build());

            final var gymModel = GymModel
                .builder()
                .contacts(contactModel)
                .addresses(addressModel)
                .build();

            final Long gymId = 1L;

            final var actual = GymMapper.createFrom(gymModel, gymId);

            assertThat(actual, samePropertyValuesAs(expected, "addresses", "contacts"));
            assertThat(actual.getContacts(), hasSize(1));
            actual.getContacts().forEach(c -> assertThat(c, samePropertyValuesAs(expectedContact)));
            assertThat(actual.getAddresses(), hasSize(1));
            actual.getAddresses().forEach(a -> assertThat(a, samePropertyValuesAs(expectedAddress)));
        }

    }

}