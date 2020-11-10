package com.itrain.gym.controller.v1;

import java.util.HashSet;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.itrain.common.resolver.UserIdResolver.UserId;
import com.itrain.gym.controller.v1.model.AddressModel;
import com.itrain.gym.controller.v1.model.ContactModel;
import com.itrain.gym.controller.v1.model.GymModel;
import com.itrain.gym.domain.Gym;
import com.itrain.gym.mapper.GymMapper;
import com.itrain.gym.service.GymService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = { "gym" })
@RequestMapping(path = { "/api/v1/gym" })
@RestController(value = "gym-controller")
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    @ApiOperation(value = "create a gym")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE })
    public Gym create(@Valid @RequestBody @NotNull final GymModel model, @ApiIgnore @UserId final Long gymId) {

        final var addresses = Objects.requireNonNullElse(model.getAddresses(), new HashSet<AddressModel>());
        addresses.forEach(a -> a.setId(null));

        final var contacts = Objects.requireNonNullElse(model.getContacts(), new HashSet<ContactModel>());
        contacts.forEach(c -> c.setId(null));

        model.setAddresses(addresses);

        model.setContacts(contacts);

        final var gym = GymMapper.createNullSafeFrom(model, gymId);

        return gymService.create(gym);
    }

    @ApiOperation(value = "get complete gym properties")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Gym find(@ApiIgnore @UserId final Long gymId) {

        return gymService.findById(gymId);
    }

}