package com.itrain.student.repository;

import com.itrain.student.domain.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "student-contact-repository")
public interface ContactRepository extends JpaRepository<Contact, Long> { }