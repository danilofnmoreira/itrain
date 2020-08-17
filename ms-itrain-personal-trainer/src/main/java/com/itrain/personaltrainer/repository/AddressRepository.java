package com.itrain.personaltrainer.repository;

import com.itrain.personaltrainer.domain.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "personal-trainer-address-repository")
public interface AddressRepository extends JpaRepository<Address, Long> { }