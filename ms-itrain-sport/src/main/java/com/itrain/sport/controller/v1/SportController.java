package com.itrain.sport.controller.v1;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.itrain.sport.controller.v1.model.SportModel;
import com.itrain.sport.domain.Sport;
import com.itrain.sport.mapper.SportMapper;
import com.itrain.sport.service.SportService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@Api(tags = { "sport" })
@RequestMapping(path = { "/api/v1/sport" })
@Log4j2
@RestController(value = "sport-controller")
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;

    @ApiOperation(value = "get all sports")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/all",
                produces = { MediaType.APPLICATION_JSON_VALUE })
    public Page<Sport> getAll(final Pageable pageable) {

        log.debug("getting all sports. Page {}", pageable);

        return sportService.getAll(pageable);
    }

    @ApiOperation(value = "find sports by ids")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Sport> findByIds(@Valid @RequestParam(name = "sport_id") @NotEmpty final Set<@NotNull @Positive Long> sportIds) {

        log.debug("find sports by ids. {}", sportIds);

        return sportService.findAllById(sportIds);
    }

    @ApiOperation(value = "delete the given set of sport ids")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@Valid @RequestBody @NotEmpty final Set<@NotNull @Positive Long> sportIds) {

        log.debug("deleting sports by ids. {}", sportIds);

        sportService.deleteAllById(sportIds);
    }

    @ApiOperation(value = "create sports")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE })
    public Set<Sport> create(@Valid @RequestBody @NotEmpty final Set<@NotNull SportModel> models) {

        log.debug("creating sports. {}", models);

        models.forEach(c-> c.setId(null));

        final var sports = SportMapper.createFrom(models);

        return sportService.createAll(sports);
    }

    @ApiOperation(value = "edit the given set of sports")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void edit(@Valid @RequestBody @NotEmpty final Set<@NotNull SportModel> models) {

        log.debug("editing sports. {}", models);

        final var sports = SportMapper.createFrom(models);

        sportService.edit(sports);
    }

}