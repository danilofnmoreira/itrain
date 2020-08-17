package com.itrain.personaltrainer.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "personal trainer domain test")
class PersonalTrainerTest {

    @Nested
    @DisplayName(value = "when two personal trainer objects has")
    class when_two_PersonalTrainer_objects_has {

        @Test
        @DisplayName(value = "null ids, they are not equals")
        void null_ids_they_are_not_equals() {

            final var personalTrainer1 = new PersonalTrainer();
            personalTrainer1.setId(null);

            final var personalTrainer2 = new PersonalTrainer();
            personalTrainer2.setId(null);

            assertThat(personalTrainer1, is(not(equalTo(personalTrainer2))));
        }

        @Test
        @DisplayName(value = "equal ids, they are equals")
        void equal_ids_they_are_equals() {

            final var personalTrainer1 = new PersonalTrainer();
            personalTrainer1.setId(1L);

            final var personalTrainer2 = new PersonalTrainer();
            personalTrainer2.setId(1L);

            assertThat(personalTrainer1, is(equalTo(personalTrainer2)));
        }

    }

    @Nested
    @DisplayName(value = "after adding a set of addresses, they must be in a mutual relationship")
    class after_adding_a_set_of_addresses_they_must_be_in_a_mutual_relationship {

        private PersonalTrainer personalTrainer;

        @BeforeEach
        void setUp() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(1L).build());
            addresses.add(Address.builder().id(2L).build());

            personalTrainer = PersonalTrainer.builder().id(1L).addresses(addresses).build();
        }

        @Test
        void test() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(3L).build());
            addresses.add(Address.builder().id(4L).build());

            personalTrainer.addAddresses(addresses);

            assertThat(personalTrainer.getAddresses(), hasSize(4));
            assertThat(personalTrainer.getAddresses().containsAll(addresses), is(true));
            addresses.forEach(a -> assertThat(a.getPersonalTrainer(), is(sameInstance(personalTrainer))));
        }

        @Test
        @DisplayName(value = "even if, the given personal trainer does not have a previewus set of addresses")
        void even_if_the_given_personal_trainer_does_not_have_a_previewus_set_of_addresses() {

            personalTrainer.setAddresses(null);

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(3L).build());
            addresses.add(Address.builder().id(4L).build());

            personalTrainer.addAddresses(addresses);

            assertThat(personalTrainer.getAddresses(), hasSize(2));
            assertThat(personalTrainer.getAddresses().containsAll(addresses), is(true));
            addresses.forEach(a -> assertThat(a.getPersonalTrainer(), is(sameInstance(personalTrainer))));
        }
    }

    @Nested
    @DisplayName(value = "after adding a set of contacts, they must be in a mutual relationship")
    class after_adding_a_set_of_contacts_they_must_be_in_a_mutual_relationship {

        private PersonalTrainer personalTrainer;

        @BeforeEach
        void setUp() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(1L).build());
            contacts.add(Contact.builder().id(2L).build());

            personalTrainer = PersonalTrainer.builder().id(1L).contacts(contacts).build();
        }

        @Test
        void test() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(3L).build());
            contacts.add(Contact.builder().id(4L).build());

            personalTrainer.addContacts(contacts);

            assertThat(personalTrainer.getContacts(), hasSize(4));
            assertThat(personalTrainer.getContacts().containsAll(contacts), is(true));
            contacts.forEach(c -> assertThat(c.getPersonalTrainer(), is(sameInstance(personalTrainer))));
        }

        @Test
        @DisplayName(value = "even if, the given personal trainer does not have a previewus set of contacts")
        void even_if_the_given_personal_trainer_does_not_have_a_previewus_set_of_contacts() {

            personalTrainer.setContacts(null);

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(3L).build());
            contacts.add(Contact.builder().id(4L).build());

            personalTrainer.addContacts(contacts);

            assertThat(personalTrainer.getContacts(), hasSize(2));
            assertThat(personalTrainer.getContacts().containsAll(contacts), is(true));
            contacts.forEach(c -> assertThat(c.getPersonalTrainer(), is(sameInstance(personalTrainer))));
        }
    }

    @Nested
    @DisplayName(value = "given a set of sport ids, it should be parsed and added to sport porperty in personal trainer")
    class given_a_set_of_sport_ids_it_should_be_parsed_and_added_to_sport_porperty_in_personal_trainer {

        private Set<Long> sportsId = Set.of(3L, 4L, 5L);
        private PersonalTrainer personalTrainer;

        @BeforeEach
        void setUp() {

            personalTrainer = new PersonalTrainer();
        }

        @Test
        @DisplayName(value = "if there are, already, some ids")
        void if_there_are_already_some_ids() {

            personalTrainer.setSports("1,2");

            personalTrainer.addParsedSports(sportsId);

            assertThat(personalTrainer.getSports(), is(equalTo("1,2,3,4,5")));
        }

        @Test
        @DisplayName(value = "if there are not some ids")
        void if_there_are_not_some_ids() {

            personalTrainer.addParsedSports(sportsId);

            assertThat(personalTrainer.getSports(), is(equalTo("3,4,5")));
        }

        @Test
        @DisplayName(value = "if the given set of ids is null or empty, do nothing")
        void if_the_given_set_of_ids_is_null_or_empty_do_nothing() {

            personalTrainer.addParsedSports(null);

            assertThat(personalTrainer.getSports(), is(nullValue()));

            personalTrainer.addParsedSports(Collections.emptySet());

            assertThat(personalTrainer.getSports(), is(nullValue()));
        }
    }

    @Nested
    @DisplayName(value = "when get parsed sports,")
    class when_get_parsed_sports {

        private PersonalTrainer personalTrainer;

        @BeforeEach
        void setUp() {

            personalTrainer = new PersonalTrainer();
        }

        @Test
        @DisplayName(value = "should return empty set if there are no sports")
        void should_return_empty_set_if_there_are_no_sports() {

            final var parsedSports = personalTrainer.getParsedSports();

            assertThat(parsedSports, is(empty()));
            assertThat(parsedSports, is(instanceOf(HashSet.class)));
        }

        @Test
        @DisplayName(value = "should return filled collection")
        void should_return_filled_collection() {

            personalTrainer.setSports("1,2,3,4");

            final var parsedSports = personalTrainer.getParsedSports();

            assertThat(parsedSports, containsInAnyOrder(1L, 2L, 3L, 4L));
        }
    }

}