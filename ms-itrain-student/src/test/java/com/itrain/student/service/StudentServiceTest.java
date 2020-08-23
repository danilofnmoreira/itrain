package com.itrain.student.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.itrain.common.exception.DuplicateEntityException;
import com.itrain.student.domain.Student;
import com.itrain.student.repository.StudentRepository;

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