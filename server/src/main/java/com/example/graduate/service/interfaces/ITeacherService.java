package com.example.graduate.service.interfaces;

import com.example.graduate.dto.TeacherDTO;
import com.example.graduate.dto.Teacher.ProjectTopicByTeacher;

import java.util.List;

public interface ITeacherService {
    List<TeacherDTO> getAllTeachers();

    //    //get project by teacher id 
    List<ProjectTopicByTeacher> getPendingTopicsByTeacher(Long teacherUserId, String keyword, boolean isApproved);

    boolean approveProject(Long projectId);
}