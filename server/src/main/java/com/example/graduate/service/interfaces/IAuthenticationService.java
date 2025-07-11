package com.example.graduate.service.interfaces;

import com.example.graduate.dto.AuthenticationRequest;
import com.example.graduate.dto.AuthenticationResponse;
import com.example.graduate.dto.RegisterRequest;
import com.example.graduate.dto.MenuPermission.MenuPermissionResponseDTO;
import com.example.graduate.models.Users;

import java.util.List;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
    List<MenuPermissionResponseDTO> getMenusWithPermissionsByUser(Users user);
    void assignRoleToUser(Long userId, Long roleId);
    Long getCurrentUserId();


    // send email function
    void sendEmail(String to,String subject,String body);

    void sendOtpToEmail(String email);

    String verifyOtp(String email, String otpCode);

    void resetPassword(String email, String newPassword);
}