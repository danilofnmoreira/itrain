package com.itrain.gym.controller.v1;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.itrain.common.resolver.UserIdResolver.UserId;
import com.itrain.gym.controller.v1.model.ProfileModel;
import com.itrain.gym.mapper.GymMapper;
import com.itrain.gym.service.GymService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = { "gym" })
@RequestMapping(path = { "/api/v1/gym/profile" })
@RestController(value = "gym-profile-controller")
@RequiredArgsConstructor
public class ProfileController {

    private final GymService gymService;

    @ApiOperation(value = "edit gym profile")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void editBiography(@Valid @RequestBody @NotNull final ProfileModel model, @ApiIgnore @UserId final Long gymId) {

        final var gym = GymMapper.createFrom(model, gymId);

        gymService.editProfile(gym);
    }
}