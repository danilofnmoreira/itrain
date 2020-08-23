package com.itrain.auth.service;

import java.util.HashSet;

import javax.transaction.Transactional;

import com.itrain.auth.controller.v1.request.signup.SignUpRequest;
import com.itrain.auth.domain.User;
import com.itrain.auth.domain.UserRole;
import com.itrain.auth.mapper.UserMapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User signUp(final SignUpRequest request) {

		final var user = UserMapper.createFrom(request);

		final String pwdEncoded = passwordEncoder.encode(user.getPassword());

		user.setPassword(pwdEncoded);

		final var roles = new HashSet<UserRole>();
		roles.add(UserRole.ROLE_USER);

		switch (request.getUserType()) {

			case STUDENT:

				roles.add(UserRole.ROLE_STUDENT);
				break;

			case GYM:

				roles.add(UserRole.ROLE_GYM);
				break;

			case PERSONAL_TRAINER:

				roles.add(UserRole.ROLE_PERSONAL_TRAINER);
				break;
		}

		user.addRoles(roles);

		return userService.create(user);
	}

}