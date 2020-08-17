package com.itrain.common.client.gym;

import java.util.Set;

import com.itrain.common.client.gym.model.Address;
import com.itrain.common.client.gym.model.Contact;
import com.itrain.common.client.gym.model.Gym;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gym-client", url = "${itrain.gym.base-url}/api/v1/gym")
public interface GymV1Client {

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    Gym createGym(@RequestBody Gym gym, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String auth);

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