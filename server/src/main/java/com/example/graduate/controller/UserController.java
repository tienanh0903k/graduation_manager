package com.example.graduate.controller;

import com.example.graduate.dto.AssignRoleRequest;
import com.example.graduate.service.interfaces.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/assign-role")
    public ResponseEntity<String> assignRoleToUser(@RequestBody AssignRoleRequest request) {
        authenticationService.assignRoleToUser(request.getUserId(), request.getRoleId());
        return ResponseEntity.ok("Role assigned successfully");
    }
}
