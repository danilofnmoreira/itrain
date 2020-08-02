package com.itrain.controller.api.v1.impl;

import com.itrain.controller.api.v1.SignInController;
import com.itrain.payload.api.v1.UserCredentials;
import com.itrain.service.SignInService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
public final class SignInControllerImpl implements SignInController {

    private final SignInService signInController;

    @Override
    public ResponseEntity<Object> signIn(final UserCredentials credentials) {

        log.debug("sign in user {}", credentials);

        final var token = signInController.signIn(credentials);

        return ResponseEntity.noContent().header(HttpHeaders.AUTHORIZATION, token).build();
    }

}