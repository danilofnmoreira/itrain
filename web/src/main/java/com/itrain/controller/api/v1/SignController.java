package com.itrain.controller.api.v1;

import java.util.Set;

import com.itrain.domain.User;
import com.itrain.domain.UserRole;
import com.itrain.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = { "/api/v1" })
@RestController
@RequiredArgsConstructor
public class SignController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = { "/signup" })
    public void signup(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRoles(Set.of(UserRole.USER, UserRole.ADMIN));
        userService.save(user);
        // signin(user);
    }

    // @ResponseStatus(value = HttpStatus.OK)
    // @PostMapping(path = { "/signin" })
    // public void signin(@RequestBody User user) {
    //     //
    // }

}