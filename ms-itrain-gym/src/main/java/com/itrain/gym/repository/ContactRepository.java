package com.itrain.gym.repository;

import com.itrain.gym.domain.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "gym-contact-repository")
public interface ContactRepository extends JpaRepository<Contact, Long> { }