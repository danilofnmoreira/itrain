package com.itrain.gym.repository;

import com.itrain.gym.domain.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "gym-address-repository")
public interface AddressRepository extends JpaRepository<Address, Long> { }