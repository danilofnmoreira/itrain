package com.itrain.student.controller.v1;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.itrain.student.controller.v1.model.AddressModel;
import com.itrain.student.domain.Address;
import com.itrain.student.mapper.AddressMapper;
import com.itrain.student.service.AddressService;
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

@Api(tags = { "address", "student address" })
@RequestMapping(path = { "/api/v1/student/addresses" })
@Log4j2
@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @ApiOperation(value = "add addresses to the given student")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Address> add(@Valid @RequestBody @NotEmpty final Set<@NotNull AddressModel> models, @ApiIgnore @UserId final Long studentId) {

        log.debug("adding addresses to student, {}. {}", studentId, models);

        models.forEach(c-> c.setId(null));

        final var addresses = AddressMapper.createFrom(models);

        return addressService.add(studentId, addresses);
    }

    @ApiOperation(value = "edit the given set of addresses")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void edit(@Valid @RequestBody @NotEmpty final Set<@NotNull AddressModel> models, @ApiIgnore @UserId final Long studentId) {

        log.debug("editing addresses for student, {}. {}", studentId, models);

        final var addresses = AddressMapper.createFrom(models);

        addressService.edit(studentId, addresses);
    }

    @ApiOperation(value = "delete the given set of address ids")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@Valid @RequestParam(name = "address_id") @NotEmpty final Set<@NotNull @Positive Long> addressIds, @ApiIgnore @UserId final Long studentId) {

        log.debug("deleteing addresses for student, {}. {}", studentId, addressIds);

        addressService.delete(studentId, addressIds);
    }

    @ApiOperation(value = "get all addresses from student")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Address> getAll(@ApiIgnore @UserId final Long studentId) {

        log.debug("getting all addresses for student, {}. {}", studentId);

        return addressService.getAll(studentId);
    }

}
