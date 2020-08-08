package com.itrain.client.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.itrain.client.domain.Address;
import com.itrain.client.domain.Client;
import com.itrain.client.domain.Contact;
import com.itrain.client.repository.ClientRepository;
import com.itrain.common.exception.DuplicateEntityException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "tests for client service layer")
class ClientServiceTest {

    private ClientService clientService;
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {

        clientRepository = mock(ClientRepository.class);
        clientService = new ClientService(clientRepository);
    }

    @Test
    @DisplayName(value = "when creatin a client, should throws DuplicateEntityException if it already exists")
    void when_creatin_a_client_should_throws_DuplicateEntityException_if_it_already_exists() {

        final var expected = new  DuplicateEntityException("Client, 1, already exists.");

        final var clientId = 1L;

        final var client = new Client();
        client.setId(clientId);

        when(clientRepository.existsById(clientId)).thenReturn(true);

        final var actual = assertThrows(DuplicateEntityException.class, () -> clientService.create(client));

        assertThat(actual.getMessage(), is(equalTo(expected.getMessage())));
    }

    @Test
    @DisplayName(value = "when creatin a client, should save it, if it does not exists")
    void when_creatin_a_client_should_save_it_if_it_does_not_exists() {

        final var now = LocalDateTime.now();

        final var clientId = 1L;

        final var addresses = new HashSet<Address>();
        addresses.add(Address.builder().id(1L).build());
        addresses.add(Address.builder().id(1L).build());

        final var contacts = new HashSet<Contact>();
        contacts.add(Contact.builder().id(1L).build());
        contacts.add(Contact.builder().id(1L).build());

        final var client = Client
            .builder()
            .id(clientId)
            .addresses(addresses)
            .contacts(contacts)
            .build();

        when(clientRepository.existsById(clientId)).thenReturn(false);
        when(clientRepository.save(client)).thenReturn(client);

        final var actual = clientService.create(client);

        assertThat(actual.getRegisteredAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getUpdatedAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getAddresses(), hasSize(2));
        assertThat(actual.getContacts(), hasSize(2));
        actual.getAddresses().forEach(a -> assertThat(a.getId(), is(nullValue())));
        actual.getContacts().forEach(c -> assertThat(c.getId(), is(nullValue())));
    }

    @Test
    @DisplayName(value = "when saving a client, should return it with properties create_at and updated_at filled with now value")
    void when_saving_a_client_should_return_it_with_properties_create_at_and_updated_at_filled_with_now_value() {

        final var now = LocalDateTime.now();

        final var client = new Client();

        when(clientService.save(client)).thenReturn(client);

        final var actual = clientService.save(client);

        assertThat(actual.getRegisteredAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getUpdatedAt(), is(greaterThanOrEqualTo(now)));
    }

    @Nested
    @DisplayName(value = "when finding client by id")
    class when_finding_client_by_id {

        private final long id = 1L;

        @Test
        @DisplayName(value = "should return the existing one")
        void should_return_the_existing_one() {

            final var expected = new Client();
            expected.setId(id);

            when(clientRepository.findById(id)).thenReturn(Optional.of(expected));

            final var actual = clientService.findById(id);

            assertThat(actual, is(equalTo(expected)));
        }

        @Test
        @DisplayName(value = "should throws NoSuchElementException when it does not exists")
        void should_throws_NoSuchElementException_when_it_does_not_exists() {

            final var expected = new NoSuchElementException("Client, 1, not found.");

            when(clientRepository.findById(id)).thenReturn(Optional.empty());

            final var actual = assertThrows(NoSuchElementException.class, () -> clientService.findById(id));

            assertThat(actual.getMessage(), is(equalTo(expected.getMessage())));
        }
    }

}