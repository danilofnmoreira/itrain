package com.itrain.student.mapper;

import java.util.Objects;

import com.itrain.student.controller.v1.model.StudentModel;
import com.itrain.student.domain.Student;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentMapper {

    public static Student createFrom(final StudentModel model, final Long studentId) {

        return Student
            .builder()
            .id(studentId)
            .contacts(ContactMapper.createFrom(model.getContacts()))
            .addresses(AddressMapper.createFrom(model.getAddresses()))
            .build();
    }

    public static Student createNullSafeFrom(final StudentModel model, final Long studentId) {

        final var nullSafeModel = Objects.requireNonNullElse(model, new StudentModel());

        return Student
            .builder()
            .id(studentId)
            .contacts(ContactMapper.createNullSafeFrom(nullSafeModel.getContacts()))
            .addresses(AddressMapper.createNullSafeFrom(nullSafeModel.getAddresses()))
            .build();
    }
}