package com.itrain.student.service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.itrain.student.domain.Contact;
import com.itrain.student.repository.ContactRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final StudentService studentService;
    private final ContactRepository contactRepository;

    @Transactional
    public Set<Contact> add(final Long studentId, final Set<Contact> contacts) {

        final var student = studentService.findById(studentId);

        contacts.forEach(c -> {

            c.setId(null);

            c.addStudent(student);

        });

        contactRepository.saveAll(contacts);

        studentService.save(student);

        return contacts;
    }

    @Transactional
    public Set<Contact> edit(final Long studentId, final Set<Contact> contacts) {

        final var student = studentService.findById(studentId);

        final var currentContacts = student.getContacts();

        contacts.retainAll(currentContacts);

        if (contacts.isEmpty()) {

            return Collections.emptySet();
        }

        contacts.forEach(c -> currentContacts
            .stream()
            .filter(c::equals)
            .forEach(cc -> cc.fillFrom(c)));

        studentService.save(student);

        return contacts;

    }

    @Transactional
	public Set<Contact> delete(final Long studentId, final Set<Long> contactIds) {

        final var student = studentService.findById(studentId);

        final var currentContacts = student.getContacts();

        final var toDelete = currentContacts
            .stream()
            .filter(c -> contactIds.contains(c.getId()))
            .collect(Collectors.toSet());

        if (toDelete.isEmpty()) {

            return Collections.emptySet();
        }

        contactRepository.deleteAll(toDelete);

        currentContacts.removeAll(toDelete);

        studentService.save(student);

        return toDelete;
	}

	public Set<Contact> getAll(Long studentId) {

        final var student = studentService.findById(studentId);

        return student.getContacts();
	}

}
