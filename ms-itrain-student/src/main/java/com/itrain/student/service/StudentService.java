package com.itrain.student.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.itrain.common.exception.DuplicateEntityException;
import com.itrain.student.domain.Address;
import com.itrain.student.domain.Contact;
import com.itrain.student.domain.Student;
import com.itrain.student.repository.StudentRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service(value = "student-service")
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student save(final Student student) {

        final var now = LocalDateTime.now();

        student.setRegisteredAt(now);
        student.setUpdatedAt(now);

        return studentRepository.save(student);
    }

    public Student findById(final Long id) {

        return findOptionalById(id).orElseThrow(() -> new NoSuchElementException(String.format("Student, %s, not found.", id)));
    }

    public Optional<Student> findOptionalById(final Long id) {

        return studentRepository.findById(id);
    }

    @Transactional
    public Student create(final Student student) {

        if (studentRepository.existsById(student.getId())) {
            throw new DuplicateEntityException(String.format("Student, %s, already exists.", student.getId()));
        }

        final var addresses = Objects.requireNonNullElse(student.getAddresses(), new HashSet<Address>());
        addresses.forEach(a -> a.setId(null));

        final var contacts = Objects.requireNonNullElse(student.getContacts(), new HashSet<Contact>());
        contacts.forEach(c -> c.setId(null));

        student.addAddresses(addresses);

        student.addContacts(contacts);

        return save(student);
    }

}