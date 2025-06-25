package com.example.graduate.service.interfaces;

import com.example.graduate.models.Users;

public interface IJwtService {
    String generateToken(Users user);
    String extractUsername(String token);
}
