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

import com.itrain.personaltrainer.domain.PersonalTrainer;
import com.itrain.personaltrainer.domain.Contact;
import com.itrain.personaltrainer.repository.ContactRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings(value = "unchecked")
@DisplayName(value = "tests for contact service layer")
class ContactServiceTest {

    private ContactService contactService;
    private PersonalTrainerService personalTrainerService;
    private ContactRepository contactRepository;

    private final Long personalTrainerId = 1L;

    private static Long contactId = 2L;

    @BeforeEach
    void setUp() {

        personalTrainerService = mock(PersonalTrainerService.class);
        contactRepository = mock(ContactRepository.class);
        contactService = new ContactService(personalTrainerService, contactRepository);
    }

    @Test
    @DisplayName(value = "given a set of contacts, should be added to the found personal trainer with the given id")
    void given_a_set_of_contacts_should_be_added_to_the_found_personal_trainer_with_the_given_id() {

        final var currentSetOfContacts = new HashSet<Contact>();
        currentSetOfContacts.add(Contact.builder().id(1L).build());

        final var personalTrainer = new PersonalTrainer();
        personalTrainer.setId(personalTrainerId);
        personalTrainer.setContacts(currentSetOfContacts);

        final var contacts = Set.of(
            new Contact(),
            Contact.builder().id(1L).build()
        );

        when(personalTrainerService.findById(personalTrainerId)).thenReturn(personalTrainer);
        given(contactRepository.saveAll(contacts)).willAnswer(invocation -> {

            final var ctts = (Iterable<Contact>) invocation.getArgument(0);

            ctts.forEach(c -> {
                if(c.getId() != null && c.getId() != 1L)
                    throw new RuntimeException("must be null");
                c.setId(contactId++);
            });
            return null;
        });

        final var actual = contactService.add(personalTrainerId, contacts);

        assertThat(personalTrainer.getContacts(), hasSize(3));
        assertThat(actual, hasSize(contacts.size()));
        actual.forEach(c -> {
            assertThat(c.getId(), is(notNullValue()));
            assertThat(c.getPersonalTrainer(), is(equalTo(personalTrainer)));
        });
    }

    @Test
    @DisplayName(value = "when adding a set of contact, if personal trainer does not exists, should throw NoSuchElementException")
    void when_adding_a_set_of_contact_if_personal_trainer_does_not_exists_should_throw_NoSuchElementException() {

        when(personalTrainerService.findById(personalTrainerId)).thenThrow(new NoSuchElementException("Personal trainer, 1, not found."));

        final var actual = assertThrows(NoSuchElementException.class, () -> contactService.add(personalTrainerId, null));

        assertThat(actual.getMessage(), is(equalTo("Personal trainer, 1, not found.")));
    }

    @Nested
    @DisplayName(value = "when editing a given set of contacts, ")
    class when_editing_a_given_set_of_contacts {

        private PersonalTrainer personalTrainer;

        @BeforeEach
        void setUp() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(1L).build());
            contacts.add(Contact.builder().id(2L).build());

            personalTrainer = PersonalTrainer.builder().id(personalTrainerId).contacts(contacts).build();
        }

        @Test
        @DisplayName(value = "should return empty set, when none of the given contacts owns for the given personal trainer")
        void should_return_empty_set_when_none_of_the_given_contacts_owns_for_the_given_personal_trainer() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(3L).build());

            when(personalTrainerService.findById(personalTrainerId)).thenReturn(personalTrainer);

            final var actual = contactService.edit(personalTrainerId, contacts);

            assertThat(actual, is(empty()));
        }

        @Test
        @DisplayName(value = "should return edited set of contacts")
        void should_return_edited_set_of_contacts() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(1L).email("email").build());
            contacts.add(Contact.builder().id(2L).email("email").build());

            when(personalTrainerService.findById(personalTrainerId)).thenReturn(personalTrainer);

            final var actual = contactService.edit(personalTrainerId, contacts);

            assertThat(actual.containsAll(contacts), is(true));
            actual.forEach(c -> assertThat(c.getEmail(), is(equalTo("email"))));
            personalTrainer.getContacts().forEach(c -> assertThat(c.getEmail(), is(equalTo("email"))));
        }
    }

    @Test
    @DisplayName(value = "given a set of contact ids, should delete them from the given contact")
    void given_a_set_of_contact_ids_should_delete_them_from_the_given_contact() {

        final var contacts = new HashSet<Contact>();
        contacts.add(Contact.builder().id(1L).build());
        contacts.add(Contact.builder().id(2L).build());

        final var personalTrainer = PersonalTrainer.builder().id(personalTrainerId).contacts(contacts).build();

        when(personalTrainerService.findById(personalTrainerId)).thenReturn(personalTrainer);

        final var actual = contactService.delete(personalTrainerId, Set.of(1L));

        assertThat(personalTrainer.getContacts(), hasSize(1));
        assertThat(actual, hasSize(1));
        actual.forEach(c -> assertThat(c.getId(), is(equalTo(1L))));
    }

    @Test
    @DisplayName(value = "given a personal trainer id, should return his all contacts")
    void given_a_personal_trainer_id_should_return_his_all_contacts() {

        final var contacts = new HashSet<Contact>();
        contacts.add(Contact.builder().id(1L).build());
        contacts.add(Contact.builder().id(2L).build());

        final var personalTrainer = PersonalTrainer.builder().id(personalTrainerId).contacts(contacts).build();

        when(personalTrainerService.findById(personalTrainerId)).thenReturn(personalTrainer);

        final var actual = contactService.getAll(personalTrainerId);

        assertThat(actual, hasSize(2));
    }
}