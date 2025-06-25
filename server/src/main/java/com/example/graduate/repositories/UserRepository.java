package com.example.graduate.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.graduate.models.Users;

//functional interface => lamda dung khong can phai khai bao
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
