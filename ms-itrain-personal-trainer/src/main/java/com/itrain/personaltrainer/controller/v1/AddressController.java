package com.itrain.personaltrainer.controller.v1;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.itrain.common.resolver.UserIdResolver.UserId;
import com.itrain.personaltrainer.controller.v1.model.AddressModel;
import com.itrain.personaltrainer.domain.Address;
import com.itrain.personaltrainer.mapper.AddressMapper;
import com.itrain.personaltrainer.service.AddressService;

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

@Api(tags = { "address", "personal trainer" })
@RequestMapping(path = { "/api/v1/personal-trainer/addresses" })
@Log4j2
@RestController(value = "personal-trainer-address-controller")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @ApiOperation(value = "add addresses to the given personal trainer")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Address> add(@Valid @RequestBody @NotEmpty final Set<@NotNull AddressModel> models, @ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("adding addresses to personal trainer, {}. {}", personalTrainerId, models);

        models.forEach(c-> c.setId(null));

        final var addresses = AddressMapper.createFrom(models);

        return addressService.add(personalTrainerId, addresses);
    }

    @ApiOperation(value = "edit the given set of addresses")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void edit(@Valid @RequestBody @NotEmpty final Set<@NotNull AddressModel> models, @ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("editing addresses for personal trainer, {}. {}", personalTrainerId, models);

        final var addresses = AddressMapper.createFrom(models);

        addressService.edit(personalTrainerId, addresses);
    }

    @ApiOperation(value = "delete the given set of address ids")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@Valid @RequestBody @NotEmpty final Set<@NotNull @Positive Long> addressIds, @ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("deleteing addresses for personal trainer, {}. {}", personalTrainerId, addressIds);

        addressService.delete(personalTrainerId, addressIds);
    }

    @ApiOperation(value = "get all addresses from personal trainer")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Address> getAll(@ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("getting all addresses for personal trainer, {}. {}", personalTrainerId);

        return addressService.getAll(personalTrainerId);
    }

}
