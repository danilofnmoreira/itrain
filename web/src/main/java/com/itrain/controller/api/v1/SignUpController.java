package com.itrain.controller.api.v1;

import javax.validation.Valid;

import com.itrain.payload.api.v1.request.signup.SignUpRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = { "/api/v1" })
public interface SignUpController {

    @PostMapping(path = { "signup" })
    void signUp(@Valid @RequestBody SignUpRequest request);

}