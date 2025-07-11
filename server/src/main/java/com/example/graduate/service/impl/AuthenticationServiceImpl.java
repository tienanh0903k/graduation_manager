package com.example.graduate.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.graduate.dto.AuthenticationRequest;
import com.example.graduate.dto.AuthenticationResponse;
import com.example.graduate.dto.RegisterRequest;
import com.example.graduate.dto.MenuPermission.MenuPermissionResponseDTO;
import com.example.graduate.dto.MenuPermission.PermissionDTO;
import com.example.graduate.mapper.UserMapper;
import com.example.graduate.models.Users;
import com.example.graduate.models.RoleName;
import com.example.graduate.models.Roles;
import com.example.graduate.models.MenuItem;
import com.example.graduate.models.OtpVerification;
import com.example.graduate.models.RoleMenuPermission;
import com.example.graduate.repositories.RoleRepository;
import com.example.graduate.repositories.UserRepository;
import com.example.graduate.repositories.OTPRepository;
import com.example.graduate.repositories.RoleMenuPermissionRepository;
import com.example.graduate.service.interfaces.IAuthenticationService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final JavaMailSender javaMailSender;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OTPRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleMenuPermissionRepository roleMenuPermissionRepository;
    private final UserMapper userMapper;

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

    @Override
    public List<MenuPermissionResponseDTO> getMenusWithPermissionsByUser(Users user) {
        Roles role = user.getRole(); // Lấy role của user

        List<RoleMenuPermission> roleMenuPermissions = roleMenuPermissionRepository.findByRole(role);

        return roleMenuPermissions.stream().map(rmp -> {
            MenuItem menu = rmp.getMenu();

            return MenuPermissionResponseDTO.builder()
                    .id(menu.getId())
                    .label(menu.getLabel())
                    .route(menu.getRoute())
                    .icon(menu.getIcon())
                    .orderNo(menu.getOrderNo())
                    .module(menu.getModule())
                    .isVisible(menu.getIsVisible())
                    .parentId(menu.getParent() != null ? menu.getParent().getId() : null)
                    .permissions(List.of(PermissionDTO.builder()
                            .canRead(rmp.isCanRead())
                            .canCreate(rmp.isCanCreate())
                            .canUpdate(rmp.isCanUpdate())
                            .canDelete(rmp.isCanDelete())
                            .build()))
                    .build();
        }).collect(Collectors.toList());
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

    @Override
    public void assignRoleToUser(Long userId, Long roleId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Roles role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
        user.setRole(role);
        userRepository.save(user);
        logger.info("Assigned role {} to user {}", role.getName(), user.getEmail());
    }

    // Phương thức tạo AuthenticationResponse
    private AuthenticationResponse buildAuthenticationResponse(String token, Users user) {
        List<MenuPermissionResponseDTO> menuPermissions = getMenusWithPermissionsByUser(user);
        return AuthenticationResponse.builder()
                .token(token)
                .userDto(userMapper.toDTO(user))
                .menuPermissions(menuPermissions)
                .message("Authentication successful")
                .build();
    }

    /**
     * Lấy id của người dùng hiện tại từ security context
     *
     * @return The current user's id.
     */
    public Long getCurrentUserId() {
        // dynamic object
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Users) {
            return ((Users) principal).getId();
        }

        throw new RuntimeException("User not authenticated");
    }

    /**
     * Gửi email chứa mã OTP đến người dùng.
     *
     * @param email Địa chỉ email của người nhận OTP.
     */
    @Transactional
    public void sendOtpToEmail(String email) {
        // Tìm người dùng theo email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với email: " + email));

        // Sinh OTP
        String otpCode = generateOtp();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        // Xoá mã OTP cũ nếu có
       otpRepository.findByUser(user).ifPresent(existingOtp -> {
        otpRepository.delete(existingOtp);
        otpRepository.flush(); // ✅ Đảm bảo xóa ngay
    });


        // Tạo bản ghi mới
        OtpVerification otp = new OtpVerification();
        otp.setUser(user);
        otp.setOtpCode(otpCode);
        otp.setExpirationTime(expirationTime);
        otpRepository.save(otp);

        // Gửi email
        String subject = "Mã xác thực OTP";
        String body = "<p>Chào bạn,</p>" +
                "<p>Mã OTP của bạn là: <strong>" + otpCode + "</strong></p>" +
                "<p>Mã này sẽ hết hạn sau 5 phút.</p>";

        sendEmail(email, subject, body);
    }


    @Override
    public String verifyOtp(String email, String otpCode) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Optional<OtpVerification> otpRecord = otpRepository.findByUserAndOtpCode(user, otpCode);
        if (otpRecord.isPresent() && otpRecord.get().getExpirationTime().isAfter(LocalDateTime.now())) {
            otpRepository.delete(otpRecord.get());
            String resetToken = jwtService.generateToken(user);

         return resetToken;
        }

        throw new RuntimeException("OTP code is invalid or has expired");
    }

    @Override
    @Transactional
    public void resetPassword(String email, String newPassword) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        otpRepository.deleteByUser(user);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }



    public void sendEmail(String to, String subject, String body) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            e.getMessage();
            throw new RuntimeException(e);
        }

    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

}
