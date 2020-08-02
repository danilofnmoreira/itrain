package com.itrain.controller.api.v1.personaltrainer;

import javax.validation.Valid;

import com.itrain.payload.api.v1.RegistrationData;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "registration data", "personal trainer" })
@RequestMapping(path = { "/api/v1/personal-trainer/registration-data" })
public interface RegistrationDataController {

    @ApiOperation(value = "update the personal trainer registration data")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    void update(@Valid @RequestBody RegistrationData registrationData);

}