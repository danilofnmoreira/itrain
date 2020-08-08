package com.itrain.student.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.Set;

import com.itrain.student.controller.v1.model.AddressModel;
import com.itrain.student.domain.Address;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "tests for address mapper")
class AddressMapperTest {

    @Nested
    @DisplayName(value = "should return an Address domain")
    class should_return_an_Address_domain {

        @Test
        @DisplayName(value = "with null properties, if the given AddressModel is null")
        void with_null_properties_if_the_given_AddressModel_is_null() {

            final var expected = new Address();

            final AddressModel addressModel = null;

            final var actual = AddressMapper.createNullSafeFrom(addressModel);

            assertThat(actual, samePropertyValuesAs(expected));
        }

        @Test
        @DisplayName(value = "with the same properties values from the given AddressModel if it is not null")
        void with_the_same_properties_values_from_the_given_AddressModel_if_it_is_not_null() {

            final var expected = Address
                .builder()
                .city("city")
                .complement("complement")
                .district("district")
                .federalUnit("federalUnit")
                .publicPlace("publicPlace")
                .zipCode("zipCode")
                .build();

            final var addressModel = AddressModel
                .builder()
                .city("city")
                .complement("complement")
                .district("district")
                .federalUnit("federalUnit")
                .publicPlace("publicPlace")
                .zipCode("zipCode")
                .build();

            final var actual = AddressMapper.createFrom(addressModel);

            assertThat(actual, samePropertyValuesAs(expected));
        }

    }

    @Nested
    @DisplayName(value = "should return a set of Addresses domain")
    class should_return_a_set_of_Addresses_domain {

        @Test
        @DisplayName(value = "without itens, if the given set of AddressModel is null")
        void without_itens_if_the_given_set_of_AddressModel_is_null() {

            final Set<AddressModel> addressesModels = null;

            final var actual = AddressMapper.createNullSafeFrom(addressesModels);

            assertThat(actual, is(emptyCollectionOf(Address.class)));
        }

        @Test
        @DisplayName(value = "with same size and Addresses with the same properties values from the given AddressModel set if it is not null")
        void with_same_size_and_Addresses_with_the_same_properties_values_from_the_given_AddressModel_set_if_it_is_not_null() {

            final var expected = Address
                .builder()
                .city("city")
                .complement("complement")
                .district("district")
                .federalUnit("federalUnit")
                .publicPlace("publicPlace")
                .zipCode("zipCode")
                .build();

            final var addressesModels = Set.of(AddressModel
                .builder()
                .city("city")
                .complement("complement")
                .district("district")
                .federalUnit("federalUnit")
                .publicPlace("publicPlace")
                .zipCode("zipCode")
                .build());

            final var actual = AddressMapper.createFrom(addressesModels);

            assertThat(actual, hasSize(1));
            actual.forEach(a -> assertThat(a, samePropertyValuesAs(expected)));
        }

    }
}