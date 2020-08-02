package com.itrain.mapper;

import com.itrain.domain.User;
import com.itrain.payload.api.v1.request.signup.SignUpRequest;

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