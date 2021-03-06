package com.itrain.auth.service;

import java.util.Collections;
import java.util.NoSuchElementException;

import com.itrain.auth.controller.v1.model.UserCredentials;
import com.itrain.auth.domain.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SignInService {

	private final AuthenticationManager authenticationManager;
	private final JWSService jwsService;

	public String signIn(final UserCredentials credentials) {

		final var authResult  = attemptAuthentication(credentials);

		return onSuccessfulAuthentication(authResult);
	}

    private Authentication attemptAuthentication(final UserCredentials credentials) {

        try {
            
            return authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()));
        } catch (final InternalAuthenticationServiceException e) {

            log.error(String.format("Error when authenticating user, %s", credentials.getUsername()), e);

            if(e.getCause() instanceof NoSuchElementException) {
                throw (NoSuchElementException) e.getCause();
            }

            throw e;
        }
    }

    private String onSuccessfulAuthentication(final Authentication authResult) {

        final var user = (User) authResult.getPrincipal();

        final var jws = jwsService.createJws(user);

        return JWSService.TOKEN_PREFIX + jws;
	}

}