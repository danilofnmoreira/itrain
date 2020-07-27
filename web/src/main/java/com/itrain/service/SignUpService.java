package com.itrain.service;

import java.util.Set;

import javax.transaction.Transactional;

import com.itrain.domain.UserRole;
import com.itrain.mapper.UserMapper;
import com.itrain.payload.api.v1.request.signup.SignUpRequest;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {

	private final UserService userService;

	@Transactional
	public void signUp(SignUpRequest request) {

		var user = UserMapper.createFrom(request, Set.of(UserRole.USER));

		userService.save(user);
	}

}