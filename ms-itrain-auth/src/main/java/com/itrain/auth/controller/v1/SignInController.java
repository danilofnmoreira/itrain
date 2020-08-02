package com.itrain.auth.controller.v1;

import javax.validation.Valid;

import com.itrain.auth.controller.v1.model.UserCredentials;
import com.itrain.auth.service.SignInService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Api(tags = { "auth", "sign in" })
@RequestMapping(path = { "/api/v1" })
@Log4j2
@RestController
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;

    @ApiOperation(value = "sign in the given user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PostMapping(path = { "/signin" },
        consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> signIn(@Valid @RequestBody UserCredentials credentials) {

        log.debug("sign in user {}", credentials);

        final var token = signInService.signIn(credentials);

        return ResponseEntity.noContent().header(HttpHeaders.AUTHORIZATION, token).build();
    }

}