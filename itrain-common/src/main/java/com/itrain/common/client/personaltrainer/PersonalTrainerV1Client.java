package com.itrain.common.client.personaltrainer;

import java.util.Set;

import com.itrain.common.client.personaltrainer.model.Address;
import com.itrain.common.client.personaltrainer.model.Contact;
import com.itrain.common.client.personaltrainer.model.PersonalTrainer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "personal-trainer-client", url = "${itrain.personal-trainer.base-url}/api/v1/personal-trainer")
public interface PersonalTrainerV1Client {

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    PersonalTrainer createPersonalTrainer(@RequestBody PersonalTrainer personalTrainer, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @PostMapping(path = { "/addresses" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    Set<Address> addAddresses(@RequestBody Set<Address> models, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @PutMapping(path = { "/addresses" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    void editAddresses(@RequestBody Set<Address> models, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @DeleteMapping(path = { "/addresses" })
    void deleteAddresses(@RequestBody Set<Long> addressIds, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @GetMapping(path = { "/addresses" })
    Set<Address> getAllAddresses(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @PostMapping(path = { "/contacts" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    Set<Contact> addContacts(@RequestBody Set<Contact> models, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @PutMapping(path = { "/contacts" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    void editContacts(@RequestBody Set<Contact> models, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @DeleteMapping(path = { "/contacts" })
    void deleteContacts(@RequestBody Set<Long> contactsIds, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

    @GetMapping(path = { "/contacts" })
    Set<Contact> getAllContacts(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

}