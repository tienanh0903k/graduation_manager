package com.example.graduate.controller;

import java.util.List;

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
public ResponseEntity<Page<StudentProjectListDTO>> searchProjects(
    @RequestParam(required = false) String classCode,
    @RequestParam(required = false) String teacherName,
    @RequestParam(required = false) String title,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
) {
    Page<StudentProjectListDTO> results = studentService.searchByFilters(classCode, teacherName, title, PageRequest.of(page, size));
    return ResponseEntity.ok(results);
}

}
