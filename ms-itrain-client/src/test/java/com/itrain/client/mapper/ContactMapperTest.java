package com.itrain.client.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.Set;

import com.itrain.client.controller.v1.model.ContactModel;
import com.itrain.client.domain.Contact;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "tests for contact mapper")
class ContactMapperTest {

    @Nested
    @DisplayName(value = "should return a Contact domain")
    class should_return_a_Contact_domain {

        @Test
        @DisplayName(value = "with null properties, if the given ContactModel is null")
        void with_null_properties_if_the_given_ContactModel_is_null() {

            var expected = new Contact();

            ContactModel contactModel = null;

            var actual = ContactMapper.createFrom(contactModel);

            assertThat(actual, samePropertyValuesAs(expected));
        }

        @Test
        @DisplayName(value = "with the same properties values from the given ContactModel if it is not null")
        void with_the_same_properties_values_from_the_given_ContactModel_if_it_is_not_null() {

            var expected = Contact
                .builder()
                .email("email")
                .name("name")
                .phone("phone")
                .whatsapp(true)
                .build();

            var contactModel = ContactModel
                .builder()
                .email("email")
                .name("name")
                .phone("phone")
                .whatsapp(true)
                .build();

            var actual = ContactMapper.createFrom(contactModel);

            assertThat(actual, samePropertyValuesAs(expected));
        }

    }

    @Nested
    @DisplayName(value = "should return a set of Contacts domain")
    class should_return_a_set_of_Contacts_domain {

        @Test
        @DisplayName(value = "without itens, if the given set of ContactModel is null")
        void without_itens_if_the_given_set_of_ContactModel_is_null() {

            Set<ContactModel> contactsModels = null;

            var actual = ContactMapper.createFrom(contactsModels);

            assertThat(actual, is(emptyCollectionOf(Contact.class)));
        }

        @Test
        @DisplayName(value = "with same size and Contacts with the same properties values from the given ContactModel set if it is not null")
        void with_same_size_and_Contacts_with_the_same_properties_values_from_the_given_ContactModel_set_if_it_is_not_null() {

            var expected = Contact
                .builder()
                .email("email")
                .name("name")
                .phone("phone")
                .whatsapp(true)
                .build();

            var contactsModels = Set.of(ContactModel
                .builder()
                .email("email")
                .name("name")
                .phone("phone")
                .whatsapp(true)
                .build());

            var actual = ContactMapper.createFrom(contactsModels);

            assertThat(actual, hasSize(1));
            actual.forEach(a -> assertThat(a, samePropertyValuesAs(expected)));
        }

    }
}