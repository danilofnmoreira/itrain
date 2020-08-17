package com.itrain.personaltrainer.service;

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

import com.itrain.personaltrainer.domain.Address;
import com.itrain.personaltrainer.domain.PersonalTrainer;
import com.itrain.personaltrainer.repository.AddressRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings(value = "unchecked")
@DisplayName(value = "tests for address service layer")
class AddressServiceTest {

    private AddressService addressService;
    private PersonalTrainerService personalTrainerService;
    private AddressRepository addressRepository;

    private final Long personalTrainerId = 1L;

    private static Long addressId = 2L;

    @BeforeEach
    void setUp() {

        personalTrainerService = mock(PersonalTrainerService.class);
        addressRepository = mock(AddressRepository.class);
        addressService = new AddressService(personalTrainerService, addressRepository);
    }

    @Test
    @DisplayName(value = "given a set of address, should be added to the found personal trainer with the given id")
    void given_a_set_of_addresses_should_be_added_to_the_found_personal_trainer_with_the_given_id() {

        final var currentSetOfAddresses = new HashSet<Address>();
        currentSetOfAddresses.add(Address.builder().id(1L).build());

        final var personalTrainer = new PersonalTrainer();
        personalTrainer.setId(personalTrainerId);
        personalTrainer.setAddresses(currentSetOfAddresses);

        final var addresses = Set.of(
            new Address(),
            Address.builder().id(1L).build()
        );

        when(personalTrainerService.findById(personalTrainerId)).thenReturn(personalTrainer);
        given(addressRepository.saveAll(addresses)).willAnswer(invocation -> {

            final var adds = (Iterable<Address>) invocation.getArgument(0);

            adds.forEach(c -> {
                if(c.getId() != null && c.getId() != 1L)
                    throw new RuntimeException("must be null");
                c.setId(addressId++);
            });
            return null;
        });

        final var actual = addressService.add(personalTrainerId, addresses);

        assertThat(personalTrainer.getAddresses(), hasSize(3));
        assertThat(actual, hasSize(addresses.size()));
        actual.forEach(c -> {
            assertThat(c.getId(), is(notNullValue()));
            assertThat(c.getPersonalTrainer(), is(equalTo(personalTrainer)));
        });
    }

    @Test
    @DisplayName(value = "when adding a set of address, if personal trainer does not exists, should throw NoSuchElementException")
    void when_adding_a_set_of_address_if_personal_trainer_does_not_exists_should_throw_NoSuchElementException() {

        when(personalTrainerService.findById(personalTrainerId)).thenThrow(new NoSuchElementException("Personal trainer, 1, not found."));

        final var actual = assertThrows(NoSuchElementException.class, () -> addressService.add(personalTrainerId, null));

        assertThat(actual.getMessage(), is(equalTo("Personal trainer, 1, not found.")));
    }

    @Nested
    @DisplayName(value = "when editing a given set of addresses, ")
    class when_editing_a_given_set_of_addresses {

        private PersonalTrainer personalTrainer;

        @BeforeEach
        void setUp() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(1L).build());
            addresses.add(Address.builder().id(2L).build());

            personalTrainer = PersonalTrainer.builder().id(personalTrainerId).addresses(addresses).build();
        }

        @Test
        @DisplayName(value = "should return empty set, when none of the given addresses owns for the given personal trainer")
        void should_return_empty_set_when_none_of_the_given_addresses_owns_for_the_given_personal_trainer() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(3L).build());

            when(personalTrainerService.findById(personalTrainerId)).thenReturn(personalTrainer);

            final var actual = addressService.edit(personalTrainerId, addresses);

            assertThat(actual, is(empty()));
        }

        @Test
        @DisplayName(value = "should return edited set of addresses")
        void should_return_edited_set_of_addresses() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(1L).zipCode("zipCode").build());
            addresses.add(Address.builder().id(2L).zipCode("zipCode").build());

            when(personalTrainerService.findById(personalTrainerId)).thenReturn(personalTrainer);

            final var actual = addressService.edit(personalTrainerId, addresses);

            assertThat(actual.containsAll(addresses), is(true));
            actual.forEach(c -> assertThat(c.getZipCode(), is(equalTo("zipCode"))));
            personalTrainer.getAddresses().forEach(c -> assertThat(c.getZipCode(), is(equalTo("zipCode"))));
        }
    }

    @Test
    @DisplayName(value = "given a set of address ids, should delete them from the given address")
    void given_a_set_of_address_ids_should_delete_them_from_the_given_address() {

        final var addresses = new HashSet<Address>();
        addresses.add(Address.builder().id(1L).build());
        addresses.add(Address.builder().id(2L).build());

        final var personalTrainer = PersonalTrainer.builder().id(personalTrainerId).addresses(addresses).build();

        when(personalTrainerService.findById(personalTrainerId)).thenReturn(personalTrainer);

        final var actual = addressService.delete(personalTrainerId, Set.of(1L));

        assertThat(personalTrainer.getAddresses(), hasSize(1));
        assertThat(actual, hasSize(1));
        actual.forEach(c -> assertThat(c.getId(), is(equalTo(1L))));
    }

    @Test
    @DisplayName(value = "given a personal trainer id, should return his all addresses")
    void given_a_personal_trainer_id_should_return_his_all_addresses() {

        final var addresses = new HashSet<Address>();
        addresses.add(Address.builder().id(1L).build());
        addresses.add(Address.builder().id(2L).build());

        final var personalTrainer = PersonalTrainer.builder().id(personalTrainerId).addresses(addresses).build();

        when(personalTrainerService.findById(personalTrainerId)).thenReturn(personalTrainer);

        final var actual = addressService.getAll(personalTrainerId);

        assertThat(actual, hasSize(2));
    }
}