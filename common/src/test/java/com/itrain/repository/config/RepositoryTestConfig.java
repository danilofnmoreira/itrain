package com.itrain.repository.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.itrain.repository")
@EntityScan(basePackages = "com.itrain.domain")
public class RepositoryTestConfig { }