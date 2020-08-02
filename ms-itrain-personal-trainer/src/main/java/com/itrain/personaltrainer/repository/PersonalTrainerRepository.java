package com.itrain.personaltrainer.repository;

import com.itrain.personaltrainer.domain.PersonalTrainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Long> { }