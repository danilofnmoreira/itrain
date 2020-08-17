package com.itrain.sport.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.itrain.sport.domain.Sport;
import com.itrain.sport.repository.SportRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@SuppressWarnings(value = "unchecked")
@DisplayName(value = "tests for sport service layer")
class SportServiceTest {

    private SportService sportService;
    private SportRepository sportRepository;

    @BeforeEach
    void setUp() {

        sportRepository = mock(SportRepository.class);
        sportService = new SportService(sportRepository);
    }

    @Test
    @DisplayName(value = "should return the set of sports found by id")
    void should_return_the_set_of_sports_found_by_id() {

        final var ids = Set.of(1L, 2L, 3L);

        final var s1 = Sport.builder().id(1L).build();
        final var s2 = Sport.builder().id(2L).build();
        final var s3 = Sport.builder().id(3L).build();

        final var found = List.of(s1, s2, s3);

        when(sportRepository.findAllById(ids)).thenReturn(found);

        final var actual = sportService.findAllById(ids);

        assertThat(actual, containsInAnyOrder(s1, s2, s3));
    }

    @Test
    @DisplayName(value = "should return a page of sports for the given page request")
    void should_return_a_page_of_sports_for_the_given_page_request() {

        final var pageable = PageRequest.of(1, 2);

        final var s1 = Sport.builder().id(1L).build();
        final var s2 = Sport.builder().id(2L).build();
        final var s3 = Sport.builder().id(3L).build();

        final var page = new PageImpl<Sport>(List.of(s1, s2, s3));

        when(sportRepository.findAll(pageable)).thenReturn(page);

        final var actual = sportService.getAll(pageable);

        assertThat(actual.getContent(), containsInAnyOrder(s1, s2, s3));
    }

    @Test
    @DisplayName(value = "should delete sports with the given set of ids")
    void should_delete_sports_with_the_given_set_of_ids() {

        final var ids = Set.of(1L, 2L);

        final var s1 = Sport.builder().id(1L).build();
        final var s2 = Sport.builder().id(2L).build();
        final var s3 = Sport.builder().id(3L).build();

        final var saved = new HashSet<Sport>(Arrays.asList(s1, s2, s3));

        final var found = List.of(s1, s2);

        when(sportRepository.findAllById(ids)).thenReturn(found);

        will(invocation -> {

            List<Sport> toRemove = (List<Sport>)invocation.getArgument(0);

            saved.removeAll(toRemove);
            return null;
        }).given(sportRepository).deleteAll(found);

        sportService.deleteAllById(ids);

        assertThat(saved, contains(s3));
    }

    @Test
    @DisplayName(value = "should return a set of created sports")
    void should_return_a_set_of_created_sports() {

        final var sports = Set.of(
            Sport.builder().name("name").build()
        );

        given(sportRepository.saveAll(sports))
            .willAnswer(invocation -> {

                final var unsaved = (Iterable<Sport>) invocation.getArgument(0);

                final var saved = new ArrayList<Sport>();

                var i = 0L;
                for(var sport : unsaved) {
                    sport.setId(++i);
                    saved.add(sport);
                }

                return saved;
            });

        final var actual = sportService.createAll(sports);

        final var expected = Sport.builder().name("name").id(1L).build();

        assertThat(actual, contains(expected));
    }

    @Test
    @DisplayName(value = "should return a set of edited sports")
    void should_return_a_set_of_edited_sports() {

        final var newSports = Set.of(
            Sport.builder().id(1L).name("newname").build()
        );

        final var oldSports = List.of(
            Sport.builder().id(1L).name("name").build()
        );

        when(sportRepository.findAllById(any()))
            .thenReturn(oldSports);

        when(sportRepository.saveAll(newSports))
            .thenReturn(new ArrayList<Sport>(newSports));

        final var actual = sportService.edit(newSports);

        for(var i : actual) {

            assertThat(i, samePropertyValuesAs(Sport.builder().id(1L).name("newname").build()));
        }
    }
}