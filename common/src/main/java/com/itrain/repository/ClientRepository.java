package com.itrain.repository;

import com.itrain.domain.Client;
import com.itrain.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, User> { }