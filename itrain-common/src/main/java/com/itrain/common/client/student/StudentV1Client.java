package com.itrain.common.client.student;

import java.util.Set;

import com.itrain.common.client.student.model.Address;
import com.itrain.common.client.student.model.Contact;
import com.itrain.common.client.student.model.Student;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "student-client", url = "${itrain.student.base-url}/api/v1/student")
public interface StudentV1Client {

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    Student createStudent(@RequestBody final Student student, @RequestHeader(name = HttpHeaders.AUTHORIZATION) final String auth);

    @PostMapping(path = { "/addresses" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    Set<Address> addAddresses(@RequestBody final Set<Address> models, @RequestHeader(name = HttpHeaders.AUTHORIZATION) final String auth);

    @PutMapping(path = { "/addresses" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    void editAddresses(@RequestBody final Set<Address> models, @RequestHeader(name = HttpHeaders.AUTHORIZATION) final String auth);

    @DeleteMapping(path = { "/addresses" })
    void deleteAddresses(@RequestParam(name = "address_id") final Set<Long> addressIds, @RequestHeader(name = HttpHeaders.AUTHORIZATION) final String auth);

    @GetMapping(path = { "/addresses" })
    Set<Address> getAllAddresses(@RequestHeader(name = HttpHeaders.AUTHORIZATION) final String auth);

    @PostMapping(path = { "/contacts" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    Set<Contact> addContacts(@RequestBody final Set<Contact> models, @RequestHeader(name = HttpHeaders.AUTHORIZATION) final String auth);

    @PutMapping(path = { "/contacts" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    void editContacts(@RequestBody final Set<Contact> models, @RequestHeader(name = HttpHeaders.AUTHORIZATION) final String auth);

    @DeleteMapping(path = { "/contacts" })
    void deleteContacts(@RequestParam(name = "contact_id") final Set<Long> contactsIds, @RequestHeader(name = HttpHeaders.AUTHORIZATION) final String auth);

    @GetMapping(path = { "/contacts" })
    Set<Contact> getAllContacts(@RequestHeader(name = HttpHeaders.AUTHORIZATION) final String auth);

}