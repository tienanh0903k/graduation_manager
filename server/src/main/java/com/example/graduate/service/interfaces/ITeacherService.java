package com.example.graduate.service.interfaces;

import com.example.graduate.dto.TeacherDTO;

import java.util.List;

public interface ITeacherService {
    List<TeacherDTO> getAllTeachers();
}