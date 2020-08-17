package com.itrain.personaltrainer.repository;

import com.itrain.personaltrainer.domain.PersonalTrainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "personal-trainer-repository")
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Long> { }