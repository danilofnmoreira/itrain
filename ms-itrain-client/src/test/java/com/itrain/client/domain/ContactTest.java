package com.itrain.client.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "Contact domain test")
class ContactTest {

    @Nested
    @DisplayName(value = "when two Contact objects has")
    class when_two_Contact_objects_has {

        @Test
        @DisplayName(value = "null ids, they are not equals")
        void null_ids_they_are_not_equals() {

            final var contact1 = new Contact();
            contact1.setId(null);

            final var contact2 = new Contact();
            contact2.setId(null);

            assertThat(contact1, is(not(equalTo(contact2))));
        }

        @Test
        @DisplayName(value = "equal ids, they are equals")
        void equal_ids_they_are_equals() {

            final var contact1 = new Contact();
            contact1.setId(1L);

            final var contact2 = new Contact();
            contact2.setId(1L);

            assertThat(contact1, is(equalTo(contact2)));
        }

    }

    @Nested
    @DisplayName(value = "after adding a client to contact, they must be in a mutual relationship")
    class after_adding_a_client_to_contact_they_must_be_in_a_mutual_relationship {

        private Client client;

        @BeforeEach
        void setUp() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(1L).build());
            contacts.add(Contact.builder().id(2L).build());

            client = Client.builder().id(1L).contacts(contacts).build();
        }

        @Test
        void test() {

            final var contact = Contact.builder().id(3L).build();

            contact.addClient(client);

            assertThat(client.getContacts(), hasSize(3));
            assertThat(contact.getClient(), is(sameInstance(client)));
            assertThat(client.getContacts(), hasItem(contact));
        }

        @Test
        @DisplayName(value = "even if, the given client does not have a previewus set of contacts")
        void even_if_the_given_client_does_not_have_a_previewus_set_of_contacts() {

            client.setContacts(null);

            final var contact = Contact.builder().id(4L).build();

            contact.addClient(client);

            assertThat(client.getContacts(), hasSize(1));
            assertThat(contact.getClient(), is(sameInstance(client)));
            assertThat(client.getContacts(), hasItem(contact));
        }
    }
}