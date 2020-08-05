package com.itrain.client.controller.v1;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.itrain.client.controller.v1.model.ClientModel;
import com.itrain.client.domain.Client;
import com.itrain.client.mapper.ClientMapper;
import com.itrain.client.service.ClientService;
import com.itrain.common.resolver.UserIdResolver.UserId;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = { "client" })
@RequestMapping(path = { "/api/v1/client" })
@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @ApiOperation(value = "create a client")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Client create(@Valid @RequestBody @NotNull final ClientModel model, @ApiIgnore @UserId final Long clientId) {

        final var client = ClientMapper.createFrom(model, clientId);

        return clientService.save(client);
    }

}