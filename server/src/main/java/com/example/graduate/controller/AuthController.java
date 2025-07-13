package com.example.graduate.controller;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.graduate.dto.AuthenticationRequest;
import com.example.graduate.dto.AuthenticationResponse;
import com.example.graduate.dto.RegisterRequest;
import com.example.graduate.response.ResponseObject;
import com.example.graduate.service.impl.AuthenticationServiceImpl;
import com.example.graduate.service.impl.JwtService;
import com.example.graduate.utils.CookieUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    private JwtService jwtService;

    // log4
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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

    // ==================================== OTP function
    // ====================================
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

    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseObject> verifyOtp(@RequestParam String email, @RequestParam String otpCode,
            HttpServletResponse response) {
        try {
            String resetToken = authenticationService.verifyOtp(email, otpCode);

            Cookie cookie = new Cookie("resetToken", resetToken);
            cookie.setHttpOnly(true); // Cookie không thể truy cập qua JavaScript
            cookie.setSecure(true); // Cookie chỉ được gửi qua kết nối HTTPS
            cookie.setPath("/"); // Cookie sẽ hợp lệ cho toàn bộ ứng dụng
            cookie.setMaxAge(60 * 60); // Cookie hết hạn sau 1 giờ (60 phút * 60 giây)
            response.addCookie(cookie);

            return ResponseEntity.ok(ResponseObject.success("Xác minh OTP thành công", resetToken));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.error("Mã OTP không hợp lệ hoặc đã hết hạn", HttpStatus.BAD_REQUEST));
        }
    }

    // @PostMapping("/reset-password")
    // public ResponseEntity<ResponseObject> resetPassword(
    //         // @CookieValue(value = "resetToken", required = true) String resetToken,
    //         HttpServletRequest request,
    //         @RequestParam String newPassword) {
    //     try {
    //         String resetToken = CookieUtils.getCookieValue(request, "resetToken");
    //         // String authHeader = request.getHeader("Authorization");
    //         logger.info("Reset token from cookie: {}", resetToken);
    //         String authHeader = resetToken;

    //         if (authHeader == null || authHeader.isEmpty()) {
    //             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                     .body(ResponseObject.error("Authorization header không được để trống", HttpStatus.BAD_REQUEST));
    //         }
    //         String token = authHeader.substring(7);

    //         // dung thu method reference
    //         String email = Optional.of(token)
    //                 .map(jwtService::extractUsername)
    //                 .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

    //         logger.info(">>>>>>>>>>>>>>>>>>>>>Email: {}", email);

    //         if (email == null) {
    //             return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    //                     .body(ResponseObject.error("Token không hợp lệ hoặc đã hết hạn", HttpStatus.UNAUTHORIZED));
    //         }

    //         authenticationService.resetPassword(email, newPassword);
    //         return ResponseEntity.ok(ResponseObject.success("Mật khẩu đã được đặt lại", null));
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body(ResponseObject.error("Lỗi: " + e.getMessage(), HttpStatus.BAD_REQUEST));
    //     }
    // }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseObject> resetPassword(
        HttpServletRequest request, 
        @RequestBody Map<String, String> body
    ) {
        try {

            String newPassword = body.get("newPassword");
            if (newPassword == null || newPassword.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseObject.error("Thiếu mật khẩu mới", HttpStatus.BAD_REQUEST));
            }


            // Lấy resetToken từ cookie bằng CookieUtils
            String resetToken = CookieUtils.getCookieValue(request, "resetToken");

            if (resetToken == null || resetToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseObject.error("Cookie resetToken không tồn tại hoặc đã hết hạn",
                                HttpStatus.BAD_REQUEST));
            }

            logger.info("Reset token from cookie: {}", resetToken);

            // Sử dụng token để lấy email từ JWT
            String email = Optional.of(resetToken)
                    .map(jwtService::extractUsername)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            logger.info("Email lấy từ token: {}", email);

            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseObject.error("Token không hợp lệ hoặc đã hết hạn", HttpStatus.UNAUTHORIZED));
            }

            // Thực hiện việc thay đổi mật khẩu
            authenticationService.resetPassword(email, newPassword);

            return ResponseEntity.ok(ResponseObject.success("Mật khẩu đã được đặt lại thành công", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.error("Lỗi: " + e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

}
