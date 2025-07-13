package com.example.graduate.controller;

import com.example.graduate.dto.AssignRoleRequest;
import com.example.graduate.service.interfaces.IAuthenticationService;
import com.example.graduate.utils.FileUtils;
import com.example.graduate.utils.S3Service;

import lombok.RequiredArgsConstructor;

import java.io.File;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final S3Service s3Service;
    private final IAuthenticationService authenticationService;

    @PostMapping("/assign-role")
    public ResponseEntity<String> assignRoleToUser(@RequestBody AssignRoleRequest request) {
        authenticationService.assignRoleToUser(request.getUserId(), request.getRoleId());
        return ResponseEntity.ok("Role assigned successfully");
    }

    @PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        File convertedFile = FileUtils.convertMultiPartToFile(file);
        String result = s3Service.uploadFile(convertedFile);
        convertedFile.delete(); // Xoá file tạm
        return ResponseEntity.ok(result);
    }   
}
