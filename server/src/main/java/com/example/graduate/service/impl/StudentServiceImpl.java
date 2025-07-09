package com.example.graduate.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.graduate.dto.StudentProject.StudentProjectListDTO;
import com.example.graduate.repositories.StudentRepository;
import com.example.graduate.service.interfaces.IStudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    // @Autowired
    // public StudentServiceImpl(StudentRepository studentRepository) {
    // this.studentRepository = studentRepository;
    // }

    @Override
    public Page<StudentProjectListDTO> searchByFilters(String classCode, String teacherName, String title,
            Pageable pageable) {
        classCode = toLikePattern(classCode);
        teacherName = toLikePattern(teacherName);
        title = toLikePattern(title);

        logger.info("Searching projects with classCode: {}, teacherName: {}, title: {}", classCode, teacherName, title);

        return studentRepository.searchByFilters(classCode, teacherName, title, pageable);
    }

    /**
     * Chuẩn hóa chuỗi tìm kiếm về dạng LIKE '%keyword%'
     * Nếu null hoặc rỗng thì trả về null.
     */
    private String toLikePattern(String input) {
        return (input == null || input.isBlank()) ? null : "%" + input.trim().toLowerCase() + "%";
    }

}
