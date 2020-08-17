package com.itrain.personaltrainer.repository;

import com.itrain.personaltrainer.domain.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "personal-trainer-contact-repository")
public interface ContactRepository extends JpaRepository<Contact, Long> { }