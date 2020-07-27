package com.itrain.controller.api.v1;

import javax.validation.Valid;

import com.itrain.payload.api.v1.request.signup.SignUpRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "sign up" })
@RequestMapping(path = { "/api/v1" })
public interface SignUpController {

    @ApiOperation(value = "sign up the given user")
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping(path = { "/signup" },
        consumes = { MediaType.APPLICATION_JSON_VALUE })
    void signUp(@Valid @RequestBody SignUpRequest request);

}