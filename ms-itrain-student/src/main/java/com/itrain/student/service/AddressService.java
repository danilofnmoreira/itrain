package com.itrain.student.service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.itrain.student.domain.Address;
import com.itrain.student.repository.AddressRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final StudentService studentService;
    private final AddressRepository addressRepository;

    @Transactional
    public Set<Address> add(final Long studentId, final Set<Address> addresses) {

        final var student = studentService.findById(studentId);

        addresses.forEach(a -> {

            a.setId(null);

            a.addStudent(student);

        });

        addressRepository.saveAll(addresses);

        studentService.save(student);

        return addresses;
    }

    @Transactional
    public Set<Address> edit(final Long studentId, final Set<Address> addresses) {

        final var student = studentService.findById(studentId);

        final var currentAddresses = student.getAddresses();

        addresses.retainAll(currentAddresses);

        if (addresses.isEmpty()) {

            return Collections.emptySet();
        }

        addresses.forEach(a -> currentAddresses
            .stream()
            .filter(a::equals)
            .forEach(ca -> ca.fillFrom(a)));

        studentService.save(student);

        return addresses;

    }

    @Transactional
	public Set<Address> delete(final Long studentId, final Set<Long> addressIds) {

        final var student = studentService.findById(studentId);

        final var currentAddresses = student.getAddresses();

        final var toDelete = currentAddresses
            .stream()
            .filter(c -> addressIds.contains(c.getId()))
            .collect(Collectors.toSet());

        if (toDelete.isEmpty()) {

            return Collections.emptySet();
        }

        addressRepository.deleteAll(toDelete);

        currentAddresses.removeAll(toDelete);

        studentService.save(student);

        return toDelete;
	}

	public Set<Address> getAll(Long studentId) {

        final var student = studentService.findById(studentId);

        return student.getAddresses();
	}

}
