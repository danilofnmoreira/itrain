package com.itrain.personaltrainer.service;

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

import com.itrain.personaltrainer.domain.Address;
import com.itrain.personaltrainer.domain.PersonalTrainer;
import com.itrain.personaltrainer.domain.Contact;
import com.itrain.personaltrainer.repository.PersonalTrainerRepository;
import com.itrain.common.exception.DuplicateEntityException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "tests for personal trainer service layer")
class PersonalTrainerServiceTest {

    private PersonalTrainerService personalTrainerService;
    private PersonalTrainerRepository personalTrainerRepository;

    @BeforeEach
    void setUp() {

        personalTrainerRepository = mock(PersonalTrainerRepository.class);
        personalTrainerService = new PersonalTrainerService(personalTrainerRepository);
    }

    @Test
    @DisplayName(value = "when creatin a personal trainer, should throws DuplicateEntityException if it already exists")
    void when_creatin_a_personalTrainer_should_throws_DuplicateEntityException_if_it_already_exists() {

        final var expected = new  DuplicateEntityException("Personal trainer, 1, already exists.");

        final var personalTrainerId = 1L;

        final var personalTrainer = new PersonalTrainer();
        personalTrainer.setId(personalTrainerId);

        when(personalTrainerRepository.existsById(personalTrainerId)).thenReturn(true);

        final var actual = assertThrows(DuplicateEntityException.class, () -> personalTrainerService.create(personalTrainer));

        assertThat(actual.getMessage(), is(equalTo(expected.getMessage())));
    }

    @Test
    @DisplayName(value = "when creatin a personal trainer, should save it, if it does not exists")
    void when_creatin_a_personal_trainer_should_save_it_if_it_does_not_exists() {

        final var now = LocalDateTime.now();

        final var personalTrainerId = 1L;

        final var addresses = new HashSet<Address>();
        addresses.add(Address.builder().id(1L).build());
        addresses.add(Address.builder().id(1L).build());

        final var contacts = new HashSet<Contact>();
        contacts.add(Contact.builder().id(1L).build());
        contacts.add(Contact.builder().id(1L).build());

        final var personalTrainer = PersonalTrainer
            .builder()
            .id(personalTrainerId)
            .addresses(addresses)
            .contacts(contacts)
            .build();

        when(personalTrainerRepository.existsById(personalTrainerId)).thenReturn(false);
        when(personalTrainerRepository.save(personalTrainer)).thenReturn(personalTrainer);

        final var actual = personalTrainerService.create(personalTrainer);

        assertThat(actual.getRegisteredAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getUpdatedAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getAddresses(), hasSize(2));
        assertThat(actual.getContacts(), hasSize(2));
        actual.getAddresses().forEach(a -> assertThat(a.getId(), is(nullValue())));
        actual.getContacts().forEach(c -> assertThat(c.getId(), is(nullValue())));
    }

    @Test
    @DisplayName(value = "when saving a personal trainer, should return it with properties create_at and updated_at filled with now value")
    void when_saving_a_personal_trainer_should_return_it_with_properties_create_at_and_updated_at_filled_with_now_value() {

        final var now = LocalDateTime.now();

        final var personalTrainer = new PersonalTrainer();

        when(personalTrainerService.save(personalTrainer)).thenReturn(personalTrainer);

        final var actual = personalTrainerService.save(personalTrainer);

        assertThat(actual.getRegisteredAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getUpdatedAt(), is(greaterThanOrEqualTo(now)));
    }

    @Nested
    @DisplayName(value = "when finding personal trainer by id")
    class when_finding_personal_trainer_by_id {

        private final long id = 1L;

        @Test
        @DisplayName(value = "should return the existing one")
        void should_return_the_existing_one() {

            final var expected = new PersonalTrainer();
            expected.setId(id);

            when(personalTrainerRepository.findById(id)).thenReturn(Optional.of(expected));

            final var actual = personalTrainerService.findById(id);

            assertThat(actual, is(equalTo(expected)));
        }

        @Test
        @DisplayName(value = "should throws NoSuchElementException when it does not exists")
        void should_throws_NoSuchElementException_when_it_does_not_exists() {

            final var expected = new NoSuchElementException("Personal trainer, 1, not found.");

            when(personalTrainerRepository.findById(id)).thenReturn(Optional.empty());

            final var actual = assertThrows(NoSuchElementException.class, () -> personalTrainerService.findById(id));

            assertThat(actual.getMessage(), is(equalTo(expected.getMessage())));
        }
    }

}