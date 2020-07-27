package com.itrain.mapper;

import java.util.Set;

import com.itrain.domain.User;
import com.itrain.domain.UserRole;
import com.itrain.payload.api.v1.request.signup.SignUpRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User createFrom(SignUpRequest request, Set<UserRole> roles) {

		var credentials = request.getCredentials();

		var user = User
			.builder()
			.username(credentials.getUsername())
			.password(credentials.getPassword())
            .build();

        user.setRoles(roles);

        return user;
    }

}