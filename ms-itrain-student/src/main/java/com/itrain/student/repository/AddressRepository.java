package com.itrain.student.repository;

import com.itrain.student.domain.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "student-address-repository")
public interface AddressRepository extends JpaRepository<Address, Long> { }