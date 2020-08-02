package com.itrain.auth.mapper;

import com.itrain.auth.controller.v1.request.signup.SignUpRequest;
import com.itrain.auth.domain.User;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User createFrom(SignUpRequest request) {

		var credentials = request.getCredentials();

		return User
			.builder()
			.username(credentials.getUsername())
			.password(credentials.getPassword())
            .build();
    }

}