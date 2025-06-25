package com.example.graduate.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.graduate.service.impl.JwtService;
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
        // Tạo đối tượng User mới từ request
        Users newUser = new Users();
        // The method getEmail() in the type Users is not applicable for the arguments
        // (String)Java(67108979)
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        // newUser.setRole(RoleName.ADMIN);
        // The method findByName(Roles) in the type RoleRepository is not applicable for
        // the arguments (RoleName)Java(67108979)
        Roles userRole = roleRepository.findByName(RoleName.STUDENT)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        // log userRole
        logger.info("Role found: {}", userRole);
        // Roles(id=2, name=USER, users=[])
        // The method getRoles() is undefined for the type UsersJava(67108964)
        newUser.setRole(userRole);

        userRepository.save(newUser);

        String token = jwtService.generateToken(newUser);

        // type return response
        return AuthenticationResponse.builder()
                .token(token)
                .userDto(UserMapper.ConvertUser(newUser))
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            // Kiểm tra người dùng có tồn tại không
            Users user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Kiểm tra mật khẩu đã được mã hóa đúng
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Invalid credentials"); // Sử dụng BadCredentialsException cho mật
                                                                          // khẩu sai
            }

            // Tạo token JWT sau khi đăng nhập thành công
            String token = jwtService.generateToken(user);

            // Trả về token và user thông qua DTO
            return AuthenticationResponse.builder()
                    .token(token)
                    .userDto(UserMapper.ConvertUser(user))
                    .build();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials", e);
        }
    }

}
