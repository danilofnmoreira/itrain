package com.itrain.personaltrainer.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "Contact domain test")
class ContactTest {

    @Nested
    @DisplayName(value = "when two Contact objects has")
    class when_two_Contact_objects_has {

        @Test
        @DisplayName(value = "null ids, they are not equals")
        void null_ids_they_are_not_equals() {

            final var contact1 = new Contact();
            contact1.setId(null);

            final var contact2 = new Contact();
            contact2.setId(null);

            assertThat(contact1, is(not(equalTo(contact2))));
        }

        @Test
        @DisplayName(value = "equal ids, they are equals")
        void equal_ids_they_are_equals() {

            final var contact1 = new Contact();
            contact1.setId(1L);

            final var contact2 = new Contact();
            contact2.setId(1L);

            assertThat(contact1, is(equalTo(contact2)));
        }

    }

    @Nested
    @DisplayName(value = "after adding a personal trainer to contact, they must be in a mutual relationship")
    class after_adding_a_personal_trainer_to_contact_they_must_be_in_a_mutual_relationship {

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

            final var contact = Contact.builder().id(3L).build();

            contact.addPersonalTrainer(personalTrainer);

            assertThat(personalTrainer.getContacts(), hasSize(3));
            assertThat(contact.getPersonalTrainer(), is(sameInstance(personalTrainer)));
            assertThat(personalTrainer.getContacts(), hasItem(contact));
        }

        @Test
        @DisplayName(value = "even if, the given personal trainer does not have a previewus set of contacts")
        void even_if_the_given_personal_trainer_does_not_have_a_previewus_set_of_contacts() {

            personalTrainer.setContacts(null);

            final var contact = Contact.builder().id(4L).build();

            contact.addPersonalTrainer(personalTrainer);

            assertThat(personalTrainer.getContacts(), hasSize(1));
            assertThat(contact.getPersonalTrainer(), is(sameInstance(personalTrainer)));
            assertThat(personalTrainer.getContacts(), hasItem(contact));
        }
    }

    @Test
    @DisplayName(value = "when fill a contact from other, they must have the same properties values")
    void when_fill_a_contact_from_other_they_must_have_the_same_properties_values() {

        final var contact = Contact
            .builder()
            .email("email")
            .id(1L)
            .name("name")
            .phone("phone")
            .whatsapp(true)
            .personalTrainer(new PersonalTrainer())
            .build();

        final var actual = new Contact();

        actual.fillFrom(contact);

        assertThat(actual, samePropertyValuesAs(contact, "personalTrainer"));
    }
}