package com.itrain.student.controller.v1;

import java.util.HashSet;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.itrain.student.controller.v1.model.AddressModel;
import com.itrain.student.controller.v1.model.StudentModel;
import com.itrain.student.controller.v1.model.ContactModel;
import com.itrain.student.domain.Student;
import com.itrain.student.mapper.StudentMapper;
import com.itrain.student.service.StudentService;
import com.itrain.common.resolver.UserIdResolver.UserId;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = { "student" })
@RequestMapping(path = { "/api/v1/student" })
@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @ApiOperation(value = "create a student")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE })
    public Student create(@Valid @RequestBody @NotNull final StudentModel model, @ApiIgnore @UserId final Long studentId) {

        final var addresses = Objects.requireNonNullElse(model.getAddresses(), new HashSet<AddressModel>());
        addresses.forEach(a -> a.setId(null));

        final var contacts = Objects.requireNonNullElse(model.getContacts(), new HashSet<ContactModel>());
        contacts.forEach(c -> c.setId(null));

        model.setAddresses(addresses);

        model.setContacts(contacts);

        final var student = StudentMapper.createNullSafeFrom(model, studentId);

        return studentService.create(student);
    }

}