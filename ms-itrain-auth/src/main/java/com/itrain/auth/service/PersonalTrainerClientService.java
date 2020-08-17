package com.itrain.auth.service;

import java.util.Set;

import com.itrain.auth.controller.v1.request.signup.SignUpRequest;
import com.itrain.common.client.personaltrainer.PersonalTrainerV1Client;
import com.itrain.common.client.personaltrainer.model.Contact;
import com.itrain.common.client.personaltrainer.model.PersonalTrainer;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonalTrainerClientService {

    private final PersonalTrainerV1Client personalTrainerV1Client;

    @Async
    public PersonalTrainer createPersonalTrainer(final SignUpRequest request, final String jws) {

        final var contacts = Set.of(
            Contact
                .builder()
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .whatsapp(request.getWhatsapp())
                .build()
        );

        final var gym = PersonalTrainer
            .builder()
            .contacts(contacts)
            .build();

        return personalTrainerV1Client.createPersonalTrainer(gym, jws);
    }

}