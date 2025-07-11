package com.example.graduate.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.graduate.models.OtpVerification;
import com.example.graduate.models.Users;

public interface OTPRepository extends JpaRepository<OtpVerification, Long> {
    void deleteByUser(Users user);

    Optional<OtpVerification> findByUserAndOtpCode(Users user, String otpCode);

    Optional<OtpVerification> findByUser(Users user);


    

}
