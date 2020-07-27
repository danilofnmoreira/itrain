package com.itrain.controller.api.v1;

import com.itrain.payload.api.v1.UserCredential;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = { "/api/v1" })
public interface SignInController {

    @PostMapping(path = { "nnkjnkjnksignin" })
    void signIn(@RequestBody UserCredential credentials);

}