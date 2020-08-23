package com.itrain.auth.service;

import java.util.NoSuchElementException;

import com.itrain.auth.domain.User;
import com.itrain.auth.repository.UserRepository;
import com.itrain.common.exception.DuplicateEntityException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(final String username) {

        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException(String.format("User, %s, not found.", username)));
    }

    public User findById(final Long id) {

        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("User, %s, not found.", id)));
    }

    public User save(final User user) {

        return userRepository.save(user);
    }

    public User create(final User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateEntityException(String.format("User, %s, already exists.", user.getUsername()));
        }

        return save(user);
    }

}