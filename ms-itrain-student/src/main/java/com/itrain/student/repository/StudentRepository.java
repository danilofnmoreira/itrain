package com.itrain.student.repository;

import com.itrain.student.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "student-repository")
public interface StudentRepository extends JpaRepository<Student, Long> { }