package com.itrain.student.service;

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

import com.itrain.student.domain.Address;
import com.itrain.student.domain.Student;
import com.itrain.student.domain.Contact;
import com.itrain.student.repository.StudentRepository;
import com.itrain.common.exception.DuplicateEntityException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "tests for student service layer")
class StudentServiceTest {

    private StudentService studentService;
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {

        studentRepository = mock(StudentRepository.class);
        studentService = new StudentService(studentRepository);
    }

    @Test
    @DisplayName(value = "when creatin a student, should throws DuplicateEntityException if it already exists")
    void when_creatin_a_student_should_throws_DuplicateEntityException_if_it_already_exists() {

        final var expected = new  DuplicateEntityException("Student, 1, already exists.");

        final var studentId = 1L;

        final var student = new Student();
        student.setId(studentId);

        when(studentRepository.existsById(studentId)).thenReturn(true);

        final var actual = assertThrows(DuplicateEntityException.class, () -> studentService.create(student));

        assertThat(actual.getMessage(), is(equalTo(expected.getMessage())));
    }

    @Test
    @DisplayName(value = "when creatin a student, should save it, if it does not exists")
    void when_creatin_a_student_should_save_it_if_it_does_not_exists() {

        final var now = LocalDateTime.now();

        final var studentId = 1L;

        final var addresses = new HashSet<Address>();
        addresses.add(Address.builder().id(1L).build());
        addresses.add(Address.builder().id(1L).build());

        final var contacts = new HashSet<Contact>();
        contacts.add(Contact.builder().id(1L).build());
        contacts.add(Contact.builder().id(1L).build());

        final var student = Student
            .builder()
            .id(studentId)
            .addresses(addresses)
            .contacts(contacts)
            .build();

        when(studentRepository.existsById(studentId)).thenReturn(false);
        when(studentRepository.save(student)).thenReturn(student);

        final var actual = studentService.create(student);

        assertThat(actual.getRegisteredAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getUpdatedAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getAddresses(), hasSize(2));
        assertThat(actual.getContacts(), hasSize(2));
        actual.getAddresses().forEach(a -> assertThat(a.getId(), is(nullValue())));
        actual.getContacts().forEach(c -> assertThat(c.getId(), is(nullValue())));
    }

    @Test
    @DisplayName(value = "when saving a student, should return it with properties create_at and updated_at filled with now value")
    void when_saving_a_student_should_return_it_with_properties_create_at_and_updated_at_filled_with_now_value() {

        final var now = LocalDateTime.now();

        final var student = new Student();

        when(studentService.save(student)).thenReturn(student);

        final var actual = studentService.save(student);

        assertThat(actual.getRegisteredAt(), is(greaterThanOrEqualTo(now)));
        assertThat(actual.getUpdatedAt(), is(greaterThanOrEqualTo(now)));
    }

    @Nested
    @DisplayName(value = "when finding student by id")
    class when_finding_student_by_id {

        private final long id = 1L;

        @Test
        @DisplayName(value = "should return the existing one")
        void should_return_the_existing_one() {

            final var expected = new Student();
            expected.setId(id);

            when(studentRepository.findById(id)).thenReturn(Optional.of(expected));

            final var actual = studentService.findById(id);

            assertThat(actual, is(equalTo(expected)));
        }

        @Test
        @DisplayName(value = "should throws NoSuchElementException when it does not exists")
        void should_throws_NoSuchElementException_when_it_does_not_exists() {

            final var expected = new NoSuchElementException("Student, 1, not found.");

            when(studentRepository.findById(id)).thenReturn(Optional.empty());

            final var actual = assertThrows(NoSuchElementException.class, () -> studentService.findById(id));

            assertThat(actual.getMessage(), is(equalTo(expected.getMessage())));
        }
    }

}