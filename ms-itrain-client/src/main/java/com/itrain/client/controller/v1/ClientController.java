package com.itrain.client.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Api(tags = { "client" })
@RequestMapping(path = { "/api/v1/client" })
@Log4j2
@RestController
@RequiredArgsConstructor
public class ClientController {

    @GetMapping(path = { "/danilo" })
    public String qwert() {

        return "client";
    }
    
}