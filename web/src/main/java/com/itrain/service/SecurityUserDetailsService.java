package com.itrain.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {

        com.itrain.domain.User found = userService.findByEmail(username);

        return User.withUsername(found.getEmail())
                    .password(found.getPassword())
                    .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(found.getRoles()))
                    .build();
    }

}