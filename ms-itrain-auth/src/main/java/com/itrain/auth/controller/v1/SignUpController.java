package com.itrain.auth.controller.v1;

import javax.validation.Valid;

import com.itrain.auth.controller.v1.request.signup.SignUpRequest;
import com.itrain.auth.service.SignInService;
import com.itrain.auth.service.SignUpService;
import com.itrain.auth.service.StudentClientService;

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

@Api(tags = { "auth" })
@RequestMapping(path = { "/api/v1" })
@Log4j2
@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;
    private final SignInService signInService;
    private final StudentClientService studentClientService;

    @ApiOperation(value = "sign up the given user")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = { "/signup" },
                 consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> signUp(@Valid @RequestBody final SignUpRequest request) {

        log.debug("sign up user {}", request);

        signUpService.signUp(request);

        final var token = signInService.signIn(request.getCredentials());

        switch (request.getUserType()) {

			case STUDENT:

                studentClientService.createStudent(request, token);
				break;

			case GYM:

				break;

			case PERSONAL_TRAINER:

				break;
        }

        return ResponseEntity.noContent().header(HttpHeaders.AUTHORIZATION, token).build();
    }

}