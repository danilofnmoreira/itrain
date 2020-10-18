package com.itrain.auth.service;

import java.util.HashSet;

import javax.transaction.Transactional;

import com.itrain.auth.controller.v1.request.signup.SignUpRequest;
import com.itrain.auth.domain.User;
import com.itrain.auth.domain.UserRole;
import com.itrain.auth.mapper.UserMapper;
import com.itrain.common.config.SendgridConfig;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class SignUpService {

	private static final String SIGN_UP_EMAIL_SUBJECT = "Confirmação de cadastro, FitMap App";

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final Email sendGridSender;
	private final SendgridConfig sendgridConfig;

	@Value("${sendgrid.signup-template-id}")
	private String signupTemplateId;
	@Value("${sendgrid.signup-base-url-confirmation-link}")
	private String signupBaseUrlConfirmationLink;

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

	@SneakyThrows
	@Async
	public void sendEmailConfirmation(final SignUpRequest signUpRequest, final String token) {

		var jws = token.replace(JWSService.TOKEN_PREFIX, "");

		var to = new Email(signUpRequest.getEmail(), signUpRequest.getName());

		var mail = new Mail();
		mail.setFrom(sendGridSender);
		mail.setTemplateId(signupTemplateId);

		var personalization = new Personalization();
		personalization.addDynamicTemplateData("subject", SIGN_UP_EMAIL_SUBJECT);
		personalization.addDynamicTemplateData("user_name", signUpRequest.getName());
		personalization.addDynamicTemplateData("confirm_link", signupBaseUrlConfirmationLink + "?token=" + jws);
		personalization.addTo(to);

		mail.addPersonalization(personalization);

		var request = new Request();
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		request.setBody(mail.build());

		var sendGrid = new SendGrid(sendgridConfig.getApiKey());
		sendGrid.api(request);

	}

}