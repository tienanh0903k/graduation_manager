package com.example.graduate.controller;


import com.example.graduate.dto.TeacherDTO;
import com.example.graduate.dto.Teacher.ProjectTopicByTeacher;
import com.example.graduate.response.ResponseObject;
import com.example.graduate.service.interfaces.IAuthenticationService;
import com.example.graduate.service.interfaces.ITeacherService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private IAuthenticationService authHelper;

    Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @GetMapping
    public ResponseEntity<ResponseObject<?>> getAllTeachers() {
        List<TeacherDTO> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(
                new ResponseObject<List<TeacherDTO>>("Lấy danh sách giảng viên thành công", HttpStatus.OK, teachers));
    }

    /**
     * Lấy danh sách đề tài theo teacher và trạng thái duyệt
     */
    @GetMapping("/project-topics")
    public List<ProjectTopicByTeacher> getProjectTopicsByTeacher(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "isApproved") boolean isApproved) {

        Long teacherUserId = authHelper.getCurrentUserId();
        logger.info("Teacher ID: {}", teacherUserId);
        return teacherService.getPendingTopicsByTeacher(teacherUserId, keyword, isApproved);
    }

    @PatchMapping("/projects/{projectId}/approve")
    public ResponseEntity<ResponseObject<?>> approveProject(@PathVariable Long projectId) {
        boolean result = teacherService.approveProject(projectId);

        if (result) {
            return ResponseEntity.ok(
                    new ResponseObject<>("Phê duyệt đề tài thành công", HttpStatus.OK, null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject<>("Không tìm thấy đề tài cần phê duyệt", HttpStatus.NOT_FOUND, null));
        }
    }

}
