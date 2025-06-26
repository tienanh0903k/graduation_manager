package com.example.graduate.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.graduate.dto.AuthenticationRequest;
import com.example.graduate.dto.AuthenticationResponse;
import com.example.graduate.dto.RegisterRequest;
import com.example.graduate.mapper.UserMapper;
import com.example.graduate.models.Users;
import com.example.graduate.models.RoleName;
import com.example.graduate.models.Roles;
import com.example.graduate.repositories.RoleRepository;
import com.example.graduate.repositories.UserRepository;
import com.example.graduate.service.interfaces.IAuthenticationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        // Tạo đối tượng User mới từ request và mã hóa mật khẩu
        Users newUser = createUserFromRequest(request);

        // Tìm và gán role cho user
        Roles userRole = findRoleByName(RoleName.STUDENT);
        newUser.setRole(userRole);

        // Lưu user vào cơ sở dữ liệu
        userRepository.save(newUser);

        // Tạo và trả về token JWT kèm theo thông tin người dùng
        String token = jwtService.generateToken(newUser);
        return buildAuthenticationResponse(token, newUser);
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            // Kiểm tra người dùng có tồn tại không và xác thực mật khẩu
            Users user = authenticateUser(request);

            // Tạo và trả về token JWT kèm theo thông tin người dùng
            logger.info("User {} logged in successfully", user.getEmail());
            String token = jwtService.generateToken(user);
            return buildAuthenticationResponse(token, user);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials", e);
        }
    }

    // Phương thức tạo User từ request
    private Users createUserFromRequest(RegisterRequest request) {
        Users user = new Users();
        user.setName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    // Phương thức tìm role theo tên
    private Roles findRoleByName(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
    }

    // Phương thức xác thực người dùng khi đăng nhập
    private Users authenticateUser(AuthenticationRequest request) {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return user;
    }

    // Phương thức tạo AuthenticationResponse
    private AuthenticationResponse buildAuthenticationResponse(String token, Users user) {
        return AuthenticationResponse.builder()
                .token(token)
                .userDto(UserMapper.ConvertUser(user))
                .build();
    }
}
