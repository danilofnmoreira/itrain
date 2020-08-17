package com.itrain.personaltrainer.controller.v1;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.itrain.common.resolver.UserIdResolver.UserId;
import com.itrain.personaltrainer.service.SportService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@Api(tags = { "sport", "personal trainer" })
@RequestMapping(path = { "/api/v1/personal-trainer/sports" })
@Log4j2
@RestController(value = "personal-trainer-sport-controller")
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;

    @ApiOperation(value = "add sports to the given personal trainer")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void add(@Valid @RequestBody @NotEmpty final Set<@NotNull @Positive Long> sportIds, @ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("adding sports to personal trainer, {}. {}", personalTrainerId, sportIds);

        sportService.add(personalTrainerId, sportIds);
    }

    @ApiOperation(value = "edit the given set of contacts")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void edit(@Valid @RequestBody @NotEmpty final Set<@NotNull @Positive Long> sportIds, @ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("editing sports for personal trainer, {}. {}", personalTrainerId, sportIds);

        sportService.edit(personalTrainerId, sportIds);
    }

    @ApiOperation(value = "get all sports from personal trainer")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Long> getAll(@ApiIgnore @UserId final Long personalTrainerId) {

        log.debug("getting all sports for personal trainer, {}. {}", personalTrainerId);

        return sportService.getAll(personalTrainerId);
    }

}
