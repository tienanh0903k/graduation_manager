package com.example.graduate.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.graduate.dto.StudentProject.StudentProjectListDTO;

public interface IStudentService {
    /**
     * get list project for role user (student)
     * @param search
     * @param pageable
     * @return
     */
    Page<StudentProjectListDTO> searchByFilters(String classCode, String teacherName, String title, Pageable pageable);

}
