package com.example.graduate.service.interfaces;

import com.example.graduate.dto.AuthenticationRequest;
import com.example.graduate.dto.AuthenticationResponse;
import com.example.graduate.dto.RegisterRequest;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
}
