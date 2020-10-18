package com.itrain.auth.controller.v1;

import javax.validation.Valid;

import com.itrain.auth.controller.v1.request.signup.SignUpRequest;
import com.itrain.auth.service.GymClientService;
import com.itrain.auth.service.JWSService;
import com.itrain.auth.service.PersonalTrainerClientService;
import com.itrain.auth.service.SignInService;
import com.itrain.auth.service.SignUpService;
import com.itrain.auth.service.StudentClientService;
import com.itrain.auth.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Api(tags = { "auth" })
@RequestMapping(path = { "/api/v1" })
@Log4j2
@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;
    private final SignInService signInService;
    private final StudentClientService studentClientService;
    private final GymClientService gymClientService;
    private final PersonalTrainerClientService personalTrainerClientService;
    private final JWSService jwsService;
    private final UserService userService;

    @ResponseBody
    @ApiOperation(value = "sign up the given user")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = { "/signup" },
                 consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void signUp(@Valid @RequestBody final SignUpRequest request) {

        log.debug("sign up user {}", request);

        var user = signUpService.signUp(request);

        final var token = signInService.signIn(request.getCredentials());

        signUpService.sendEmailConfirmation(request, token);

        switch (request.getUserType()) {

			case STUDENT:

                studentClientService.createStudent(request, token);
				break;

			case GYM:

                gymClientService.createGym(request, token);
				break;

			case PERSONAL_TRAINER:

                personalTrainerClientService.createPersonalTrainer(request, token);
				break;
        }

        userService.lockUser(user.getId());
    }

    @ApiOperation(value = "confirm sign up with email confirmation")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = { "/confirm-signup" })
    public String confirmSignUp(@RequestParam(name = "token") final String token) {

        var claims = jwsService.parseJws(token);
        var userId = jwsService.getUserId(claims);

        userService.unlockUser(userId);

        return "unlocked-user";
    }

}