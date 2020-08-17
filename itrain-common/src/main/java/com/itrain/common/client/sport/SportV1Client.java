package com.itrain.common.client.sport;

import java.util.Set;

import com.itrain.common.client.sport.model.Sport;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sport-client", url = "${itrain.sport.base-url}/api/v1/sport")
public interface SportV1Client {

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    Set<Sport> createSport(@RequestBody Set<Sport> sports, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    void editSports(@RequestBody Set<Sport> sports, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @DeleteMapping
    void deleteSports(@RequestBody Set<Long> sportIds, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @GetMapping
    Set<Sport> findSports(@RequestParam(name = "sport_id") Set<Long> sportIds, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @GetMapping(path = { "/all" })
    Page<Sport> getAll(Pageable pageable, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

}