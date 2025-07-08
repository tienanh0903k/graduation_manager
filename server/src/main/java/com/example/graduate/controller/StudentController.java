package com.example.graduate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.graduate.dto.StudentProject.StudentProjectListDTO;
import com.example.graduate.service.interfaces.IStudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final IStudentService studentService;

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentProjectListDTO>> searchProjects(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<StudentProjectListDTO> projects = studentService.searchStudentProjects(keyword,
                PageRequest.of(page - 1, size));
        return ResponseEntity.ok(projects.getContent());

    }

}
