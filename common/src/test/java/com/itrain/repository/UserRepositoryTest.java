package com.itrain.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;
import java.util.List;

import com.itrain.domain.Client;
import com.itrain.domain.Contact;
import com.itrain.domain.User;
import com.itrain.repository.config.RepositoryTestConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@Disabled
@DisplayName(value = "user repository test layer")
@ContextConfiguration(classes = { RepositoryTestConfig.class })
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    private User u1, u2;
    private LocalDateTime now;

    //@formatter:off
    @BeforeEach
    void setUp() {

        now = LocalDateTime.now();

        u1 = User
            .builder()
            .username("u1@email.com")
            .password("password")
            .roles("ROLE_USER,ROLE_ADMIN")
            .registeredAt(now)
            .updatedAt(now)
            .build();

        u2 = User
            .builder()
            .username("u2@email.com")
            .password("password")
            .roles("USER_ROLE")
            .client(Client
                .builder()
                .contact(new Contact("name", "email", "phone", true))
                .registeredAt(now)
                .updatedAt(now)
                .build())
            .registeredAt(now)
            .updatedAt(now)
            .build();

        repo.saveAll(List.of(u1, u2));

    }
    //@formatter:on

    @Test
    @DisplayName(value = "when call findByUsername, should return the user with the given username")
    void when_call_findByUsername_should_return_the_user_with_the_given_username() {

        var actual = repo.findByUsername("u1@email.com").get();

        assertThat(actual, is(equalTo(u1)));
    }

    @Test
    @DisplayName(value = "when persiste a client in cascade way, both sould should be linked")
    void when_persiste_a_client_in_cascade_way_both_sould_should_be_linked() {

        var actual = repo.findByUsername("u2@email.com").get();

        assertThat(actual, is(equalTo(u2)));
        assertThat(actual.getClient(), is(equalTo(u2.getClient())));
    }
}