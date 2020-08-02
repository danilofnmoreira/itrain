package com.itrain.gym.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Api(tags = { "gym" })
@RequestMapping(path = { "/api/v1/gym" })
@Log4j2
@RestController
@RequiredArgsConstructor
public class GymController {

    @GetMapping(path = { "/danilo" })
    public String qwert() {

        return "gym";
    }
    
}