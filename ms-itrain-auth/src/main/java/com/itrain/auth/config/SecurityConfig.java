package com.itrain.auth.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrain.auth.service.JWSService;
import com.itrain.auth.service.UserService;
import com.itrain.common.mapper.ErrorResponseMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService) {

        return userService::findByUsername;
    }

    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        private final PasswordEncoder passwordEncoder;
        private final UserDetailsService userDetailsService;
        private final JWSService jwsService;
        private final ObjectMapper objectMapper;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                .csrf()
                    .disable()
                .cors()
                    .configurationSource(exchange -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers("/api/*/signin", "/api/*/signup").permitAll()
                        .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint((request, response, authException) -> {

                        var status = HttpStatus.UNAUTHORIZED;
                        var errorResponse = ErrorResponseMapper.createFrom(authException, status, request.getRequestURI());
                        var jsonResponse = objectMapper.writeValueAsString(errorResponse);
                        response.setStatus(status.value());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.getWriter().write(jsonResponse);
                        response.flushBuffer();
                    })
                .and()
                .addFilter(new BasicAuthenticationFilter(authenticationManager()) {

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
                                jwsService.getUserId(claims),
                                null,
                                jwsService.getAuthorities(claims)
                            ));

                        chain.doFilter(request, response);
                    }
                });
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                .ignoring()
                    //swagger
                    .antMatchers("/", "/v2/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**");
        }

        @Bean(name = { "authenticationManager" })
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

}