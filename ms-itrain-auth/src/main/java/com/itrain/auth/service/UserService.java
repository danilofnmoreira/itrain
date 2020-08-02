package com.itrain.auth.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import com.itrain.auth.domain.User;
import com.itrain.auth.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException(String.format("User, %s, not found.", username)));
    }

    public User findById(Long id) {

        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("User, %s, not found.", id)));
    }

    public User save(User user) {

        var now = LocalDateTime.now();

        user.setRegisteredAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }

}