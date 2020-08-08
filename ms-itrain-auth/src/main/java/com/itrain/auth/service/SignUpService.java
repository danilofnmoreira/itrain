package com.itrain.auth.service;

import java.util.Set;

import javax.transaction.Transactional;

import com.itrain.auth.controller.v1.request.signup.SignUpRequest;
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
	public void signUp(SignUpRequest request) {

		var user = UserMapper.createFrom(request);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		switch (request.getUserType()) {

			case STUDENT:

				user.addRoles(Set.of(UserRole.ROLE_STUDENT, UserRole.ROLE_USER));
				userService.save(user);
				break;

			case GYM:

				user.addRoles(Set.of(UserRole.ROLE_GYM, UserRole.ROLE_USER));
				userService.save(user);
				break;

			case PERSONAL_TRAINER:

				user.addRoles(Set.of(UserRole.ROLE_PERSONAL_TRAINER, UserRole.ROLE_USER));
				userService.save(user);
				break;
		}

	}

}