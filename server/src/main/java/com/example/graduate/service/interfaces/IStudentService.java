package com.example.graduate.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.graduate.dto.StudentProject.StudentProjectListDTO;
import com.example.graduate.dto.Students.SearchStudentDTO;
import com.example.graduate.dto.Students.StudentDTO;
import com.example.graduate.models.Student;

public interface IStudentService {
    /**
     * get list project for role user (student)
     * @param search
     * @param pageable
     * @return
     */
    Page<StudentProjectListDTO> searchByFilters(String classCode, String teacherName, String title, Pageable pageable);

    /**
     * 
     */

     void addImportedStudents(List<StudentDTO> students);

     Page<Student> searchStudents(SearchStudentDTO searchStudentDTO, Pageable pageable) ;

}
