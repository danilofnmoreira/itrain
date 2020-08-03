package com.itrain.client.controller.v1;

import com.itrain.client.service.ClientService;
import com.itrain.common.resolver.UserIdResolver.UserId;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = { "client" })
@RequestMapping(path = { "/api/v1/client" })
@Log4j2
@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping(path = { "/danilo" })
    public String create(@ApiIgnore @UserId(nullable = true) Long userId) {

        System.out.println("\n\n\n" + userId + "\n\n\n");

        return "client";
    }
    
}