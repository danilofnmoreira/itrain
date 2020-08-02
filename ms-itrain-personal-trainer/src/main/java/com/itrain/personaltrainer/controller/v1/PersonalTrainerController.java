package com.itrain.personaltrainer.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Api(tags = { "personal trainer" })
@RequestMapping(path = { "/api/v1/personal-trainer" })
@Log4j2
@RestController
@RequiredArgsConstructor
public class PersonalTrainerController {

    @GetMapping(path = { "/danilo" })
    public String qwert() {

        return "personal-trainer";
    }
    
}