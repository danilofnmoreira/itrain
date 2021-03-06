package com.itrain.gym.repository;

import com.itrain.gym.domain.Gym;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "gym-repository")
public interface GymRepository extends JpaRepository<Gym, Long> { }