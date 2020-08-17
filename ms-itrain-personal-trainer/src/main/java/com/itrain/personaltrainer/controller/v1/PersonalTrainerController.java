package com.itrain.personaltrainer.controller.v1;

import java.util.HashSet;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.itrain.common.resolver.UserIdResolver.UserId;
import com.itrain.personaltrainer.controller.v1.model.AddressModel;
import com.itrain.personaltrainer.controller.v1.model.ContactModel;
import com.itrain.personaltrainer.controller.v1.model.PersonalTrainerModel;
import com.itrain.personaltrainer.domain.PersonalTrainer;
import com.itrain.personaltrainer.mapper.PersonalTrainerMapper;
import com.itrain.personaltrainer.service.PersonalTrainerService;

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

@Api(tags = { "personal trainer" })
@RequestMapping(path = { "/api/v1/personal-trainer" })
@RestController(value = "personal-trainer-controller")
@RequiredArgsConstructor
public class PersonalTrainerController {

    private final PersonalTrainerService personalTrainerService;

    @ApiOperation(value = "create a personal trainer")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE })
    public PersonalTrainer create(@Valid @RequestBody @NotNull final PersonalTrainerModel model, @ApiIgnore @UserId final Long personalTrainerId) {

        final var addresses = Objects.requireNonNullElse(model.getAddresses(), new HashSet<AddressModel>());
        addresses.forEach(a -> a.setId(null));

        final var contacts = Objects.requireNonNullElse(model.getContacts(), new HashSet<ContactModel>());
        contacts.forEach(c -> c.setId(null));

        model.setAddresses(addresses);

        model.setContacts(contacts);

        final var personalTrainer = PersonalTrainerMapper.createNullSafeFrom(model, personalTrainerId);

        return personalTrainerService.create(personalTrainer);
    }

}