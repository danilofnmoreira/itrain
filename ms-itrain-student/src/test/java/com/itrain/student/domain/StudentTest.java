package com.itrain.student.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "Student domain test")
class StudentTest {

    @Nested
    @DisplayName(value = "when two Student objects has")
    class when_two_Student_objects_has {

        @Test
        @DisplayName(value = "null ids, they are not equals")
        void null_ids_they_are_not_equals() {

            final var student1 = new Student();
            student1.setId(null);

            final var student2 = new Student();
            student2.setId(null);

            assertThat(student1, is(not(equalTo(student2))));
        }

        @Test
        @DisplayName(value = "equal ids, they are equals")
        void equal_ids_they_are_equals() {

            final var student1 = new Student();
            student1.setId(1L);

            final var student2 = new Student();
            student2.setId(1L);

            assertThat(student1, is(equalTo(student2)));
        }

    }

    @Nested
    @DisplayName(value = "after adding a set of addresses, they must be in a mutual relationship")
    class after_adding_a_set_of_addresses_they_must_be_in_a_mutual_relationship {

        private Student student;

        @BeforeEach
        void setUp() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(1L).build());
            addresses.add(Address.builder().id(2L).build());

            student = Student.builder().id(1L).addresses(addresses).build();
        }

        @Test
        void test() {

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(3L).build());
            addresses.add(Address.builder().id(4L).build());

            student.addAddresses(addresses);

            assertThat(student.getAddresses(), hasSize(4));
            assertThat(student.getAddresses().containsAll(addresses), is(true));
            addresses.forEach(a -> assertThat(a.getStudent(), is(sameInstance(student))));
        }

        @Test
        @DisplayName(value = "even if, the given student does not have a previewus set of addresses")
        void even_if_the_given_student_does_not_have_a_previewus_set_of_addresses() {

            student.setAddresses(null);

            final var addresses = new HashSet<Address>();
            addresses.add(Address.builder().id(3L).build());
            addresses.add(Address.builder().id(4L).build());

            student.addAddresses(addresses);

            assertThat(student.getAddresses(), hasSize(2));
            assertThat(student.getAddresses().containsAll(addresses), is(true));
            addresses.forEach(a -> assertThat(a.getStudent(), is(sameInstance(student))));
        }
    }

    @Nested
    @DisplayName(value = "after adding a set of contacts, they must be in a mutual relationship")
    class after_adding_a_set_of_contacts_they_must_be_in_a_mutual_relationship {

        private Student student;

        @BeforeEach
        void setUp() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(1L).build());
            contacts.add(Contact.builder().id(2L).build());

            student = Student.builder().id(1L).contacts(contacts).build();
        }

        @Test
        void test() {

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(3L).build());
            contacts.add(Contact.builder().id(4L).build());

            student.addContacts(contacts);

            assertThat(student.getContacts(), hasSize(4));
            assertThat(student.getContacts().containsAll(contacts), is(true));
            contacts.forEach(c -> assertThat(c.getStudent(), is(sameInstance(student))));
        }

        @Test
        @DisplayName(value = "even if, the given student does not have a previewus set of contacts")
        void even_if_the_given_student_does_not_have_a_previewus_set_of_contacts() {

            student.setContacts(null);

            final var contacts = new HashSet<Contact>();
            contacts.add(Contact.builder().id(3L).build());
            contacts.add(Contact.builder().id(4L).build());

            student.addContacts(contacts);

            assertThat(student.getContacts(), hasSize(2));
            assertThat(student.getContacts().containsAll(contacts), is(true));
            contacts.forEach(c -> assertThat(c.getStudent(), is(sameInstance(student))));
        }
    }
}