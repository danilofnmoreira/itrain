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

import com.itrain.client.domain.Address;
import com.itrain.client.domain.Client;
import com.itrain.client.repository.AddressRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings(value = "unchecked")
@DisplayName(value = "tests for address service layer")
class AddressServiceTest {

    private AddressService addressService;
    private ClientService clientService;
    private AddressRepository addressRepository;

    private final Long clientId = 1L;

    private static Long addressId = 2L;

    @BeforeEach
    void setUp() {

        clientService = mock(ClientService.class);
        addressRepository = mock(AddressRepository.class);
        addressService = new AddressService(clientService, addressRepository);
    }

    @Test
    @DisplayName(value = "given a set of address, should be added to the found client with the given id")
    void given_a_set_of_addresses_should_be_added_to_the_found_client_with_the_given_id() {

        final var currentSetOfAddresses = new HashSet<Address>();
        currentSetOfAddresses.add(Address.builder().id(1L).build());

        final var client = new Client();
        client.setId(clientId);
        client.setAddresses(currentSetOfAddresses);

        final var addresses = Set.of(
            new Address(),
            Address.builder().id(1L).build()
        );

        when(clientService.findById(clientId)).thenReturn(client);
        given(addressRepository.saveAll(addresses)).willAnswer(invocation -> {

            final var adds = (Iterable<Address>) invocation.getArgument(0);

            adds.forEach(c -> {
                if(c.getId() != null && c.getId() != 1L)
                    throw new RuntimeException("must be null");
                c.setId(addressId++);
            });
            return null;
        });

        final var actual = addressService.add(clientId, addresses);

        assertThat(client.getAddresses(), hasSize(3));
        assertThat(actual, hasSize(addresses.size()));
        actual.forEach(c -> {
            assertThat(c.getId(), is(notNullValue()));
            assertThat(c.getClient(), is(equalTo(client)));
        });
    }

    @Test
    @DisplayName(value = "when adding a set of address, if client does not exists, should throw NoSuchElementException")
    void when_adding_a_set_of_address_if_client_does_not_exists_should_throw_NoSuchElementException() {

        when(clientService.findById(clientId)).thenThrow(new NoSuchElementException("Client, 1, not found."));

        final var actual = assertThrows(NoSuchElementException.class, () -> addressService.add(clientId, null));

        assertThat(actual.getMessage(), is(equalTo("Client, 1, not found.")));
    }

    @Nested
    @DisplayName(value = "when editing a given set of addresses, ")
    class when_editing_a_given_set_of_addresses {

        private Client client;

        @BeforeEach
        void setUp() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(1L).build());
            addresses.add(Address.builder().id(2L).build());

            client = Client.builder().id(clientId).addresses(addresses).build();
        }

        @Test
        @DisplayName(value = "should return empty set, when none of the given addresses owns for the given client")
        void should_return_empty_set_when_none_of_the_given_addresses_owns_for_the_given_client() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(3L).build());

            when(clientService.findById(clientId)).thenReturn(client);

            final var actual = addressService.edit(clientId, addresses);

            assertThat(actual, is(empty()));
        }

        @Test
        @DisplayName(value = "should return edited set of addresses")
        void should_return_edited_set_of_addresses() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(1L).zipCode("zipCode").build());
            addresses.add(Address.builder().id(2L).zipCode("zipCode").build());

            when(clientService.findById(clientId)).thenReturn(client);

            final var actual = addressService.edit(clientId, addresses);

            assertThat(actual.containsAll(addresses), is(true));
            actual.forEach(c -> assertThat(c.getZipCode(), is(equalTo("zipCode"))));
            client.getAddresses().forEach(c -> assertThat(c.getZipCode(), is(equalTo("zipCode"))));
        }
    }

    @Test
    @DisplayName(value = "given a set of address ids, should delete them from the given address")
    void given_a_set_of_address_ids_should_delete_them_from_the_given_address() {

        final var addresses = new HashSet<Address>();
        addresses.add(Address.builder().id(1L).build());
        addresses.add(Address.builder().id(2L).build());

        final var client = Client.builder().id(clientId).addresses(addresses).build();

        when(clientService.findById(clientId)).thenReturn(client);

        final var actual = addressService.delete(clientId, Set.of(1L));

        assertThat(client.getAddresses(), hasSize(1));
        assertThat(actual, hasSize(1));
        actual.forEach(c -> assertThat(c.getId(), is(equalTo(1L))));
    }

    @Test
    @DisplayName(value = "given a client id, should return his all addresses")
    void given_a_client_id_should_return_his_all_addresses() {

        final var addresses = new HashSet<Address>();
        addresses.add(Address.builder().id(1L).build());
        addresses.add(Address.builder().id(2L).build());

        final var client = Client.builder().id(clientId).addresses(addresses).build();

        when(clientService.findById(clientId)).thenReturn(client);

        final var actual = addressService.getAll(clientId);

        assertThat(actual, hasSize(2));
    }
}