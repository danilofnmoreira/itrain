package com.itrain.controller.api.v1;

import javax.validation.Valid;

import com.itrain.payload.api.v1.UserCredentials;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "sign in" })
@RequestMapping(path = { "/api/v1" })
public interface SignInController {

    @ApiOperation(value = "sign in the given user")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PostMapping(path = { "/signin" },
        consumes = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<Object> signIn(@Valid @RequestBody UserCredentials credentials);

}