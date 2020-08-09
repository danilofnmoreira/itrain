package com.itrain.auth.service;

import java.util.Set;

import com.itrain.auth.controller.v1.request.signup.SignUpRequest;
import com.itrain.common.client.gym.GymV1Client;
import com.itrain.common.client.gym.model.Contact;
import com.itrain.common.client.gym.model.Gym;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GymClientService {

    private final GymV1Client gymV1Client;

    @Async
    public Gym createStudent(final SignUpRequest request, final String jws) {

        final var contacts = Set.of(
            Contact
                .builder()
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .whatsapp(request.getWhatsapp())
                .build()
        );

        final var gym = Gym
            .builder()
            .contacts(contacts)
            .build();

        return gymV1Client.createGym(gym, jws);
    }

}