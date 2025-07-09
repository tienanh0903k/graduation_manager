package com.example.graduate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.graduate.dto.AuthenticationRequest;
import com.example.graduate.dto.AuthenticationResponse;
import com.example.graduate.dto.RegisterRequest;
import com.example.graduate.response.ResponseObject;
import com.example.graduate.service.impl.AuthenticationServiceImpl;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = authenticationService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthenticationResponse.builder()
                            .message("Invalid username or password")
                            .build());
        }
    }


    //==================================== OTP function ====================================
      /**
     * Gửi mã OTP đến email của người dùng.
     *
     * @param email Địa chỉ email của người nhận OTP.
     * @return Thông báo thành công hay thất bại.
     */
    @PostMapping("/send-otp")
    public ResponseEntity<ResponseObject> sendOtp(@RequestParam String email) {
        try {
            authenticationService.sendOtpToEmail(email);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseObject.success("Mã OTP đã được gửi tới email của bạn!", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.error("Lỗi: " + e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
}
