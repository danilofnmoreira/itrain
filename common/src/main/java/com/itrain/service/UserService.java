package com.itrain.service;

import java.util.NoSuchElementException;

import com.itrain.domain.User;
import com.itrain.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException(String.format("User, %s, not found.", username)));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}