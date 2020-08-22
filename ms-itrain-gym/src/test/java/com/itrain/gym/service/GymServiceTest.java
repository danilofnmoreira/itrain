package com.itrain.gym.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.itrain.gym.domain.Address;
import com.itrain.gym.domain.Gym;
import com.itrain.gym.domain.Contact;
import com.itrain.gym.repository.GymRepository;
import com.itrain.common.exception.DuplicateEntityException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "tests for gym service layer")
class GymServiceTest {

    private GymService gymService;
    private GymRepository gymRepository;

    @BeforeEach
    void setUp() {

        gymRepository = mock(GymRepository.class);
        gymService = new GymService(gymRepository);
    }

    @Test
    @DisplayName(value = "when creatin a gym, should throws DuplicateEntityException if it already exists")
    void when_creatin_a_gym_should_throws_DuplicateEntityException_if_it_already_exists() {

        final var expected = new  DuplicateEntityException("Gym, 1, already exists.");

        final var gymId = 1L;

        final var gym = new Gym();
        gym.setId(gymId);

        when(gymRepository.existsById(gymId)).thenReturn(true);

        final var actual = assertThrows(DuplicateEntityException.class, () -> gymService.create(gym));

        assertThat(actual.getMessage(), is(equalTo(expected.getMessage())));
    }

    @Test
    @DisplayName(value = "when creatin a gym, should save it, if it does not exists")
    void when_creatin_a_gym_should_save_it_if_it_does_not_exists() {

        final var now = ZonedDateTime.now();

        final var gymId = 1L;

        final var addresses = new HashSet<Address>();
        addresses.add(Address.builder().id(1L).build());
        addresses.add(Address.builder().id(1L).build());

        final var contacts = new HashSet<Contact>();
        contacts.add(Contact.builder().id(1L).build());
        contacts.add(Contact.builder().id(1L).build());

        final var gym = Gym
            .builder()
            .id(gymId)
            .addresses(addresses)
            .contacts(contacts)
            .build();

        when(gymRepository.existsById(gymId)).thenReturn(false);
        when(gymRepository.save(gym)).thenReturn(gym);

        final var actual = gymService.create(gym);

        assertThat(actual.getRegisteredAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getUpdatedAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getAddresses(), hasSize(2));
        assertThat(actual.getContacts(), hasSize(2));
        actual.getAddresses().forEach(a -> assertThat(a.getId(), is(nullValue())));
        actual.getContacts().forEach(c -> assertThat(c.getId(), is(nullValue())));
    }

    @Test
    @DisplayName(value = "when saving a gym, should return it with properties create_at and updated_at filled with now value")
    void when_saving_a_gym_should_return_it_with_properties_create_at_and_updated_at_filled_with_now_value() {

        final var now = ZonedDateTime.now();

        final var gym = new Gym();

        when(gymService.save(gym)).thenReturn(gym);

        final var actual = gymService.save(gym);

        assertThat(actual.getRegisteredAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getUpdatedAt(), is(greaterThanOrEqualTo(now)));
    }

    @Nested
    @DisplayName(value = "when finding gym by id")
    class when_finding_gym_by_id {

        private final long id = 1L;

        @Test
        @DisplayName(value = "should return the existing one")
        void should_return_the_existing_one() {

            final var expected = new Gym();
            expected.setId(id);

            when(gymRepository.findById(id)).thenReturn(Optional.of(expected));

            final var actual = gymService.findById(id);

            assertThat(actual, is(equalTo(expected)));
        }

        @Test
        @DisplayName(value = "should throws NoSuchElementException when it does not exists")
        void should_throws_NoSuchElementException_when_it_does_not_exists() {

            final var expected = new NoSuchElementException("Gym, 1, not found.");

            when(gymRepository.findById(id)).thenReturn(Optional.empty());

            final var actual = assertThrows(NoSuchElementException.class, () -> gymService.findById(id));

            assertThat(actual.getMessage(), is(equalTo(expected.getMessage())));
        }
    }

}