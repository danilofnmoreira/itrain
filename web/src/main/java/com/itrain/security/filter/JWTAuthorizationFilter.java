package com.itrain.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itrain.security.service.JWSService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWSService jwsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWSService jwsService) {
        super(authenticationManager);
        this.jwsService = jwsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JWSService.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        var jws = authHeader.replace(JWSService.TOKEN_PREFIX, "");
        var claims = jwsService.parseJws(jws);

        SecurityContextHolder
            .getContext()
            .setAuthentication(new UsernamePasswordAuthenticationToken(
                jwsService.getSubject(claims),
                null,
                jwsService.getAuthorities(claims)
            ));

        chain.doFilter(request, response);
    }

}