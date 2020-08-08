package com.itrain.student.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.Collections;
import java.util.Set;

import com.itrain.student.controller.v1.model.AddressModel;
import com.itrain.student.controller.v1.model.StudentModel;
import com.itrain.student.controller.v1.model.ContactModel;
import com.itrain.student.domain.Address;
import com.itrain.student.domain.Student;
import com.itrain.student.domain.Contact;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName(value = "tests for student mapper")
class StudentMapperTest {

    @Nested
    @DisplayName(value = "should return a Student domain")
    class should_return_an_Student_domain {

        @Test
        @DisplayName(value = "with null properties, if the given StudentModel is null")
        void with_null_properties_if_the_given_StudentModel_is_null() {

            final var expected = Student
                .builder()
                .id(1L)
                .addresses(Collections.emptySet())
                .contacts(Collections.emptySet())
                .build();

            final StudentModel studentModel = null;

            final Long studentId = 1L;

            final var actual = StudentMapper.createNullSafeFrom(studentModel, studentId);

            assertThat(actual, samePropertyValuesAs(expected));
        }

        @Test
        @DisplayName(value = "with the same properties values from the given StudentModel if it is not null")
        void with_the_same_properties_values_from_the_given_StudentModel_if_it_is_not_null() {

            final var expectedAddress = Address
                .builder()
                .city("city")
                .complement("complement")
                .district("district")
                .federalUnit("federalUnit")
                .publicPlace("publicPlace")
                .zipCode("zipCode")
                .build();

            final var expectedContact = Contact
                .builder()
                .email("email")
                .name("name")
                .phone("phone")
                .whatsapp(true)
                .build();

            final var expected = Student
                .builder()
                .id(1L)
                .addresses(Set.of(expectedAddress))
                .contacts(Set.of(expectedContact))
                .build();

            final var contactModel = Set.of(ContactModel
                .builder()
                .email("email")
                .name("name")
                .phone("phone")
                .whatsapp(true)
                .build());

            final var addressModel = Set.of(AddressModel
                .builder()
                .city("city")
                .complement("complement")
                .district("district")
                .federalUnit("federalUnit")
                .publicPlace("publicPlace")
                .zipCode("zipCode")
                .build());

            final var studentModel = StudentModel
                .builder()
                .contacts(contactModel)
                .addresses(addressModel)
                .build();

            final Long studentId = 1L;

            final var actual = StudentMapper.createFrom(studentModel, studentId);

            assertThat(actual, samePropertyValuesAs(expected, "addresses", "contacts"));
            assertThat(actual.getContacts(), hasSize(1));
            actual.getContacts().forEach(c -> assertThat(c, samePropertyValuesAs(expectedContact)));
            assertThat(actual.getAddresses(), hasSize(1));
            actual.getAddresses().forEach(a -> assertThat(a, samePropertyValuesAs(expectedAddress)));
        }

    }

}