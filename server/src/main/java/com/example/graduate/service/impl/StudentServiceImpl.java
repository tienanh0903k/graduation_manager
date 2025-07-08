package com.example.graduate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.graduate.dto.StudentProject.StudentProjectListDTO;
import com.example.graduate.repositories.StudentRepository;
import com.example.graduate.service.interfaces.IStudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements  IStudentService {


     private final StudentRepository studentRepository;

    // @Autowired
    // public StudentServiceImpl(StudentRepository studentRepository) {
    //     this.studentRepository = studentRepository;
    // }

    public Page<StudentProjectListDTO> searchStudentProjects(String search, Pageable pageable) {
        return studentRepository.search(search, pageable);
    }

}
