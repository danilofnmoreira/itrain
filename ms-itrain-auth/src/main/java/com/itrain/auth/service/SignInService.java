package com.itrain.auth.service;

import java.util.Collections;
import java.util.NoSuchElementException;

import com.itrain.auth.controller.v1.model.UserCredentials;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SignInService {

	private final AuthenticationManager authenticationManager;
	private final JWSService jwsService;

	public String signIn(UserCredentials credentials) {

		var authResult  = attemptAuthentication(credentials);

		return onSuccessfulAuthentication(authResult);
	}

    private Authentication attemptAuthentication(UserCredentials credentials) {

        try {
            
            return authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()));
        } catch (InternalAuthenticationServiceException e) {

            log.error(String.format("Error when authenticating user, %s", credentials.getUsername()), e);

            if(e.getCause() instanceof NoSuchElementException) {
                throw (NoSuchElementException) e.getCause();
            }

            throw e;
        }
    }

    private String onSuccessfulAuthentication(Authentication authResult) {

        var user = ((UserDetails) authResult.getPrincipal());

        var jws = jwsService.createJws(user);

        return JWSService.TOKEN_PREFIX + jws;
	}

}