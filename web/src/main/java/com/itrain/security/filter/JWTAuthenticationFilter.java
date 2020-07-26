package com.itrain.security.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrain.security.util.JWSUtil;

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
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JWTAuthenticationFilter(String url, AuthenticationManager authenticationManager) {
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(url, "POST"));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {

            var creds = new ObjectMapper().readValue(request.getInputStream(), UserCredential.class);

            return getAuthenticationManager()
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.emptyList()));

        } catch (Exception e) {
            log.error("login error", e);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        var user = ((UserDetails) authResult.getPrincipal());

        var jws = JWSUtil.createJws(user);

        response.addHeader(HttpHeaders.AUTHORIZATION, JWSUtil.TOKEN_PREFIX + jws);
    }

}

@Getter
@Setter
class UserCredential {
    private String username;
    private String password;
}