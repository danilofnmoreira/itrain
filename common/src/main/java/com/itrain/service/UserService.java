package com.itrain.service;

import java.time.LocalDateTime;
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

        var now = LocalDateTime.now();

        user.setRegisteredAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }

    public User saveClient(User user) {

        var now = LocalDateTime.now();

        var client = user.getClient();

        client.setRegisteredAt(now);
        client.setUpdatedAt(now);

        return save(user);
    }

    public User saveGym(User user) {

        var now = LocalDateTime.now();

        var gym = user.getGym();

        gym.setRegisteredAt(now);
        gym.setUpdatedAt(now);

        return save(user);
    }

    public User savePersonalTrainer(User user) {

        var now = LocalDateTime.now();

        var personalTrainer = user.getPersonalTrainer();

        personalTrainer.setRegisteredAt(now);
        personalTrainer.setUpdatedAt(now);

        return save(user);
    }

}