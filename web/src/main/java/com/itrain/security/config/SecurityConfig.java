package com.itrain.security.config;

import com.itrain.security.filter.JWTAuthenticationFilter;
import com.itrain.security.filter.JWTAuthorizationFilter;
import com.itrain.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        return username -> {
            var found = userService.findByUsername(username);
            return User.withUsername(found.getUsername())
                       .password(found.getPassword())
                       .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(found.getRoles()))
                       .build();
        };
    }

    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        private final PasswordEncoder passwordEncoder;
        private final UserDetailsService userDetailsService;

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
                    .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name()))
                .and()
                .addFilter(new JWTAuthenticationFilter("/api/v1/signin", authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
        }
    }

}