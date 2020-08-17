package com.itrain.personaltrainer.controller.v1;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.itrain.common.resolver.UserIdResolver.UserId;
import com.itrain.personaltrainer.controller.v1.model.ContactModel;
import com.itrain.personaltrainer.domain.Contact;
import com.itrain.personaltrainer.mapper.ContactMapper;
import com.itrain.personaltrainer.service.ContactService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

@Api(tags = { "contact", "personal trainer" })
@RequestMapping(path = { "/api/v1/personal-trainer/contacts" })
@Log4j2
@RestController(value = "personal-trainer-contact-controller")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @ApiOperation(value = "add contacts to the given personal trainer")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Contact> add(@Valid @RequestBody @NotEmpty final Set<@NotNull ContactModel> models, @ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("adding contacts to personal trainer, {}. {}", personalTrainerId, models);

        models.forEach(c-> c.setId(null));

        final var contacts = ContactMapper.createFrom(models);

        return contactService.add(personalTrainerId, contacts);
    }

    @ApiOperation(value = "edit the given set of contacts")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void edit(@Valid @RequestBody @NotEmpty final Set<@NotNull ContactModel> models, @ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("editing contacts for personal trainer, {}. {}", personalTrainerId, models);

        final var contacts = ContactMapper.createFrom(models);

        contactService.edit(personalTrainerId, contacts);
    }

    @ApiOperation(value = "delete the given set of contact ids")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@Valid @RequestBody @NotEmpty final Set<@NotNull @Positive Long> contactIds, @ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("deleteing contacts for personal trainer, {}. {}", personalTrainerId, contactIds);

        contactService.delete(personalTrainerId, contactIds);
    }

    @ApiOperation(value = "get all contacts from personal trainer")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Contact> getAll(@ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("getting all contacts for personal trainer, {}. {}", personalTrainerId);

        return contactService.getAll(personalTrainerId);
    }

}
