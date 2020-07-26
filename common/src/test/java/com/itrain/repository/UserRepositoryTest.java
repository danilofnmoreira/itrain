package com.itrain.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import com.itrain.domain.User;
import com.itrain.repository.config.RepositoryTestConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DisplayName(value = "user repository test layer")
@ContextConfiguration(classes = { RepositoryTestConfig.class })
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    private User u1, u2;

    //@formatter:off
    @BeforeEach
    void setUp() {

        u1 = User
            .builder()
            .email("u1@email.com")
            .username("u1@email.com")
            .name("u1name")
            .password("password")
            .roles("USER,ADMIN")
            .build();

        u2 = User
            .builder()
            .email("u2@email.com")
            .username("u2@email.com")
            .name("u2name")
            .password("password")
            .roles("USER")
            .build();

        repo.saveAll(List.of(u1, u2));

    }
    //@formatter:on

    @Test
    @DisplayName(value = "given a email, should return them")
    void given_a_email_should_return_them() {

        var actual = repo.findByUsername("u1@email.com").get();

        assertThat(actual, is(equalTo(u1)));
    }
}