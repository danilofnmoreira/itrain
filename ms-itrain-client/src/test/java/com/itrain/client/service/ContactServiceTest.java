package com.itrain.client.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import com.itrain.client.domain.Client;
import com.itrain.client.domain.Contact;
import com.itrain.client.repository.ContactRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings(value = "unchecked")
@DisplayName(value = "tests for contact service layer")
class ContactServiceTest {

    private ContactService contactService;
    private ClientService clientService;
    private ContactRepository contactRepository;

    private final Long clientId = 1L;

    private static Long contactId = 2L;

    @BeforeEach
    void setUp() {

        clientService = mock(ClientService.class);
        contactRepository = mock(ContactRepository.class);
        contactService = new ContactService(clientService, contactRepository);
    }

    @Test
    @DisplayName(value = "given a set of clients, should be added to the found client with the given id")
    void given_a_set_of_clients_should_be_added_to_the_found_client_with_the_given_id() {

        final var currentSetOfContacts = new HashSet<Contact>();
        currentSetOfContacts.add(Contact.builder().id(1L).build());

        final var client = new Client();
        client.setId(clientId);
        client.setContacts(currentSetOfContacts);

        final var contacts = Set.of(
            new Contact(),
            Contact.builder().id(1L).build()
        );

        when(clientService.findById(clientId)).thenReturn(client);
        given(contactRepository.saveAll(contacts)).willAnswer(invocation -> {

            final var ctts = (Iterable<Contact>) invocation.getArgument(0);

            ctts.forEach(c -> {
                if(c.getId() != null && c.getId() != 1L)
                    throw new RuntimeException("must be null");
                c.setId(contactId++);
            });
            return null;
        });

        final var actual = contactService.add(clientId, contacts);

        assertThat(client.getContacts(), hasSize(3));
        assertThat(actual, hasSize(contacts.size()));
        actual.forEach(c -> {
            assertThat(c.getId(), is(notNullValue()));
            assertThat(c.getClient(), is(equalTo(client)));
        });
    }

    @Test
    @DisplayName(value = "when adding a set of contact, if client does not exists, should throw NoSuchElementException")
    void when_adding_a_set_of_contact_if_client_does_not_exists_should_throw_NoSuchElementException() {

        when(clientService.findById(clientId)).thenThrow(new NoSuchElementException("Client, 1, not found."));

        final var actual = assertThrows(NoSuchElementException.class, () -> contactService.add(clientId, null));

        assertThat(actual.getMessage(), is(equalTo("Client, 1, not found.")));
    }

    @Nested
    @DisplayName(value = "when editing a given set of contacts, ")
    class when_editing_a_given_set_of_contacts {

        private Client client;

        @BeforeEach
        void setUp() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(1L).build());
            contacts.add(Contact.builder().id(2L).build());

            client = Client.builder().id(clientId).contacts(contacts).build();
        }

        @Test
        @DisplayName(value = "should return empty set, when none of the given contacts owns for the given client")
        void should_return_empty_set_when_none_of_the_given_contacts_owns_for_the_given_client() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(3L).build());

            when(clientService.findById(clientId)).thenReturn(client);

            final var actual = contactService.edit(clientId, contacts);

            assertThat(actual, is(empty()));
        }

        @Test
        @DisplayName(value = "should return edited set of contacts")
        void should_return_edited_set_of_contacts() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(1L).email("email").build());
            contacts.add(Contact.builder().id(2L).email("email").build());

            when(clientService.findById(clientId)).thenReturn(client);

            final var actual = contactService.edit(clientId, contacts);

            assertThat(actual.containsAll(contacts), is(true));
            actual.forEach(c -> assertThat(c.getEmail(), is(equalTo("email"))));
            client.getContacts().forEach(c -> assertThat(c.getEmail(), is(equalTo("email"))));
        }
    }

    @Test
    @DisplayName(value = "given a set of contact ids, should delete them from the given contact")
    void given_a_set_of_contact_ids_should_delete_them_from_the_given_contact() {

        final var contacts = new HashSet<Contact>();
        contacts.add(Contact.builder().id(1L).build());
        contacts.add(Contact.builder().id(2L).build());

        final var client = Client.builder().id(clientId).contacts(contacts).build();

        when(clientService.findById(clientId)).thenReturn(client);

        final var actual = contactService.delete(clientId, Set.of(1L));

        assertThat(client.getContacts(), hasSize(1));
        assertThat(actual, hasSize(1));
        actual.forEach(c -> assertThat(c.getId(), is(equalTo(1L))));
    }

}