package com.itrain.client.controller.v1;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.itrain.client.controller.v1.model.AddressModel;
import com.itrain.client.domain.Address;
import com.itrain.client.mapper.AddressMapper;
import com.itrain.client.service.AddressService;
import com.itrain.common.resolver.UserIdResolver.UserId;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = { "address", "client address" })
@RequestMapping(path = { "/api/v1/client/addresses" })
@Log4j2
@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @ApiOperation(value = "add addresses to the given client")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Address> add(@Valid @RequestBody @NotEmpty final Set<@NotNull AddressModel> models, @ApiIgnore @UserId final Long clientId) {

        log.debug("adding addresses to client, {}. {}", clientId, models);

        models.forEach(c-> c.setId(null));

        final var addresses = AddressMapper.createFrom(models);

        return addressService.add(clientId, addresses);
    }

    @ApiOperation(value = "edit the given set of addresses")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void edit(@Valid @RequestBody @NotEmpty final Set<@NotNull AddressModel> models, @ApiIgnore @UserId final Long clientId) {

        log.debug("editing addresses for client, {}. {}", clientId, models);

        final var addresses = AddressMapper.createFrom(models);

        addressService.edit(clientId, addresses);
    }

    @ApiOperation(value = "delete the given set of address ids")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@Valid @RequestParam(name = "address_id") @NotEmpty final Set<@NotNull @Positive Long> addressIds, @ApiIgnore @UserId final Long clientId) {

        log.debug("deleteing addresses for client, {}. {}", clientId, addressIds);

        addressService.delete(clientId, addressIds);
    }

    @ApiOperation(value = "get all addresses from client")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Address> getAll(@ApiIgnore @UserId final Long clientId) {

        log.debug("getting all addresses for client, {}. {}", clientId);

        return addressService.getAll(clientId);
    }

}
