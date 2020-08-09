package com.itrain.auth.service;

import java.util.Set;

import com.itrain.auth.controller.v1.request.signup.SignUpRequest;
import com.itrain.common.client.student.StudentV1Client;
import com.itrain.common.client.student.model.Contact;
import com.itrain.common.client.student.model.Student;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentClientService {

    private final StudentV1Client studentV1Client;

    @Async
    public Student createStudent(final SignUpRequest request, final String jws) {

        final var contacts = Set.of(
            Contact
                .builder()
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .whatsapp(request.getWhatsapp())
                .build()
        );

        final var student = Student
            .builder()
            .contacts(contacts)
            .build();

        return studentV1Client.createStudent(student, jws);
    }

}