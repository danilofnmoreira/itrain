package com.itrain.security.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrain.security.service.JWSService;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JWSService jwsService;

    public JWTAuthenticationFilter(String url, AuthenticationManager authenticationManager, JWSService jwsService) {
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(url, "POST"));
        setAuthenticationManager(authenticationManager);
        this.jwsService = jwsService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        var creds = new ObjectMapper().readValue(request.getInputStream(), UserCredential.class);

        return getAuthenticationManager()
            .authenticate(
                new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(),
                    Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        var user = ((UserDetails) authResult.getPrincipal());

        var jws = jwsService.createJws(user);

        response.addHeader(HttpHeaders.AUTHORIZATION, JWSService.TOKEN_PREFIX + jws);
    }

}

@Getter
@Setter
class UserCredential {
    private String username;
    private String password;
}