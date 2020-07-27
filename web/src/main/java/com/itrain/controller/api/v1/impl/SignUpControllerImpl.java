package com.itrain.controller.api.v1.impl;

import com.itrain.controller.api.v1.SignUpController;
import com.itrain.payload.api.v1.request.signup.SignUpRequest;
import com.itrain.service.SignUpService;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
public class SignUpControllerImpl implements SignUpController {

    private final SignUpService signUpService;

    @Override
    public void signUp(SignUpRequest request) {

        log.debug("sign up user {}", request);

        signUpService.signUp(request);
    }

}