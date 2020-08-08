package com.itrain.client.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "Client domain test")
class ClientTest {

    @Nested
    @DisplayName(value = "when two Client objects has")
    class when_two_Client_objects_has {

        @Test
        @DisplayName(value = "null ids, they are not equals")
        void null_ids_they_are_not_equals() {

            final var client1 = new Client();
            client1.setId(null);

            final var client2 = new Client();
            client2.setId(null);

            assertThat(client1, is(not(equalTo(client2))));
        }

        @Test
        @DisplayName(value = "equal ids, they are equals")
        void equal_ids_they_are_equals() {

            final var client1 = new Client();
            client1.setId(1L);

            final var client2 = new Client();
            client2.setId(1L);

            assertThat(client1, is(equalTo(client2)));
        }

    }

    @Nested
    @DisplayName(value = "after adding a set of addresses, they must be in a mutual relationship")
    class after_adding_a_set_of_addresses_they_must_be_in_a_mutual_relationship {

        private Client client;

        @BeforeEach
        void setUp() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(1L).build());
            addresses.add(Address.builder().id(2L).build());

            client = Client.builder().id(1L).addresses(addresses).build();
        }

        @Test
        void test() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(3L).build());
            addresses.add(Address.builder().id(4L).build());

            client.addAddresses(addresses);

            assertThat(client.getAddresses(), hasSize(4));
            assertThat(client.getAddresses().containsAll(addresses), is(true));
            addresses.forEach(a -> assertThat(a.getClient(), is(sameInstance(client))));
        }

        @Test
        @DisplayName(value = "even if, the given client does not have a previewus set of addresses")
        void even_if_the_given_client_does_not_have_a_previewus_set_of_addresses() {

            client.setAddresses(null);

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(3L).build());
            addresses.add(Address.builder().id(4L).build());

            client.addAddresses(addresses);

            assertThat(client.getAddresses(), hasSize(2));
            assertThat(client.getAddresses().containsAll(addresses), is(true));
            addresses.forEach(a -> assertThat(a.getClient(), is(sameInstance(client))));
        }
    }

    @Nested
    @DisplayName(value = "after adding a set of contacts, they must be in a mutual relationship")
    class after_adding_a_set_of_contacts_they_must_be_in_a_mutual_relationship {

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

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(3L).build());
            contacts.add(Contact.builder().id(4L).build());

            client.addContacts(contacts);

            assertThat(client.getContacts(), hasSize(4));
            assertThat(client.getContacts().containsAll(contacts), is(true));
            contacts.forEach(c -> assertThat(c.getClient(), is(sameInstance(client))));
        }

        @Test
        @DisplayName(value = "even if, the given client does not have a previewus set of contacts")
        void even_if_the_given_client_does_not_have_a_previewus_set_of_contacts() {

            client.setContacts(null);

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(3L).build());
            contacts.add(Contact.builder().id(4L).build());

            client.addContacts(contacts);

            assertThat(client.getContacts(), hasSize(2));
            assertThat(client.getContacts().containsAll(contacts), is(true));
            contacts.forEach(c -> assertThat(c.getClient(), is(sameInstance(client))));
        }
    }
}