package com.itrain.gym.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.itrain.common.exception.DuplicateEntityException;
import com.itrain.gym.domain.Gym;
import com.itrain.gym.repository.GymRepository;

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