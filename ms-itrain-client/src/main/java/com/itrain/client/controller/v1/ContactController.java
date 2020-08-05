package com.itrain.client.controller.v1;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.itrain.client.controller.v1.model.ContactModel;
import com.itrain.client.domain.Contact;
import com.itrain.client.mapper.ContactMapper;
import com.itrain.client.service.ContactService;
import com.itrain.common.resolver.UserIdResolver.UserId;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = { "contact", "client concat" })
@RequestMapping(path = { "/api/v1/client/contacts" })
@Log4j2
@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @ApiOperation(value = "add contacts to the given client")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Contact> add(@Valid @RequestBody @NotEmpty final Set<@NotNull ContactModel> models, @ApiIgnore @UserId final Long clientId) {

        log.debug("adding contacts to client, {}. {}", clientId, models);

        final var contacts = ContactMapper.createFrom(models);

        return contactService.add(clientId, contacts);
    }

    @ApiOperation(value = "edit the given set of contacts")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Contact> edit(@Valid @RequestBody @NotEmpty final Set<@NotNull ContactModel> models, @ApiIgnore @UserId final Long clientId) {

        log.debug("editing contacts for client, {}. {}", clientId, models);

        final var contacts = ContactMapper.createFrom(models);

        return contactService.edit(clientId, contacts);
    }

}
