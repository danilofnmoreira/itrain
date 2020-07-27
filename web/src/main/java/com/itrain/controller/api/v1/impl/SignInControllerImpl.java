package com.itrain.controller.api.v1.impl;

import com.itrain.controller.api.v1.SignInController;
import com.itrain.payload.api.v1.UserCredential;
import com.itrain.service.SignInService;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
public class SignInControllerImpl implements SignInController {

    private final SignInService signInController;

    @Override
    public void signIn(UserCredential credentials) {

        log.debug("sign in user {}", credentials);

        signInController.signIn(credentials);
    }

}