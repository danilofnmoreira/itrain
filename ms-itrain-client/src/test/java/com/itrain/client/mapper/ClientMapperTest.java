package com.itrain.client.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.Set;

import com.itrain.client.controller.v1.model.AddressModel;
import com.itrain.client.controller.v1.model.ClientModel;
import com.itrain.client.controller.v1.model.ContactModel;
import com.itrain.client.domain.Address;
import com.itrain.client.domain.Client;
import com.itrain.client.domain.Contact;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "tests for client mapper")
class ClientMapperTest {

    @Nested
    @DisplayName(value = "should return a Client domain")
    class should_return_an_Client_domain {

        @Test
        @DisplayName(value = "with null properties, if the given ClientModel is null")
        void with_null_properties_if_the_given_ClientModel_is_null() {

            var expected = Client
                .builder()
                .id(1L)
                .addresses(Collections.emptySet())
                .contacts(Collections.emptySet())
                .build();

            ClientModel clientModel = null;

            Long clientId = 1L;

            var actual = ClientMapper.createFrom(clientModel, clientId);

            assertThat(actual, samePropertyValuesAs(expected));
        }

        @Test
        @DisplayName(value = "with the same properties values from the given ClientModel if it is not null")
        void with_the_same_properties_values_from_the_given_ClientModel_if_it_is_not_null() {

            var expectedAddress = Address
                .builder()
                .city("city")
                .complement("complement")
                .district("district")
                .federalUnit("federalUnit")
                .publicPlace("publicPlace")
                .zipCode("zipCode")
                .build();

            var expectedContact = Contact
                .builder()
                .email("email")
                .name("name")
                .phone("phone")
                .whatsapp(true)
                .build();

            var expected = Client
                .builder()
                .id(1L)
                .addresses(Set.of(expectedAddress))
                .contacts(Set.of(expectedContact))
                .build();

            var contactModel = Set.of(ContactModel
                .builder()
                .email("email")
                .name("name")
                .phone("phone")
                .whatsapp(true)
                .build());

            var addressModel = Set.of(AddressModel
                .builder()
                .city("city")
                .complement("complement")
                .district("district")
                .federalUnit("federalUnit")
                .publicPlace("publicPlace")
                .zipCode("zipCode")
                .build());

            var clientModel = ClientModel
                .builder()
                .contacts(contactModel)
                .addresses(addressModel)
                .build();

            Long clientId = 1L;

            var actual = ClientMapper.createFrom(clientModel, clientId);

            assertThat(actual, samePropertyValuesAs(expected));
            assertThat(actual.getContacts(), hasSize(1));
            actual.getContacts().forEach(c -> assertThat(c, samePropertyValuesAs(expectedContact)));
            assertThat(actual.getAddresses(), hasSize(1));
            actual.getAddresses().forEach(a -> assertThat(a, samePropertyValuesAs(expectedAddress)));
        }

    }

}