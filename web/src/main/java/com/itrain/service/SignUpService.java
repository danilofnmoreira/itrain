package com.itrain.service;

import java.util.Set;

import javax.transaction.Transactional;

import com.itrain.domain.Client;
import com.itrain.domain.Gym;
import com.itrain.domain.PersonalTrainer;
import com.itrain.domain.UserRole;
import com.itrain.mapper.ContactMapper;
import com.itrain.mapper.UserMapper;
import com.itrain.payload.api.v1.request.signup.SignUpRequest;

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

		var contacts = Set.of(ContactMapper.createFrom(request));

		switch (request.getUserType()) {

			case CLIENT:
				var client = new Client();
				client.setContacts(contacts);
				user.addClient(client);
				user.setRoles(Set.of(UserRole.ROLE_CLIENT, UserRole.ROLE_USER));
				userService.saveClient(user);
				break;

			case GYM:
				var gym = new Gym();
				gym.setContacts(contacts);
				user.addGym(gym);
				user.setRoles(Set.of(UserRole.ROLE_GYM, UserRole.ROLE_USER));
				userService.saveGym(user);
				break;

			case PERSONAL_TRAINER:
				var personalTrainer = new PersonalTrainer();
				personalTrainer.setContacts(contacts);
				user.addPersonalTrainer(personalTrainer);
				user.setRoles(Set.of(UserRole.ROLE_PERSONAL_TRAINER, UserRole.ROLE_USER));
				userService.savePersonalTrainer(user);
				break;
		}

	}

}