package com.example.graduate.controller;

import com.example.graduate.dto.TeacherDTO;
import com.example.graduate.response.ResponseObject;
import com.example.graduate.service.interfaces.ITeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllTeachers() {
        List<TeacherDTO> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(
                new ResponseObject("Lấy danh sách giảng viên thành công", HttpStatus.OK, teachers)
        );
    }
}
