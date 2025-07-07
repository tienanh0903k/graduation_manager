package com.example.graduate.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.graduate.dto.ProjectTopicDTO;
import com.example.graduate.dto.ProjectTopicFullDto;
import com.example.graduate.mapper.ProjectTopicMapper;
import com.example.graduate.models.ProjectTopic;
import com.example.graduate.models.Student;
import com.example.graduate.models.StudentProject;
import com.example.graduate.repositories.ProjectTopicRepository;
import com.example.graduate.repositories.StudentProjectRepository;
import com.example.graduate.repositories.StudentRepository;
import com.example.graduate.service.interfaces.IProjectTopicService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ProjectTopicServiceImpl implements IProjectTopicService {

    private final ProjectTopicRepository projectTopicRepository;
    private final ProjectTopicMapper projectTopicMapper;
    private final StudentRepository studentRepository;
    private final StudentProjectRepository studentProjectRepository;

    // log
    private static final Logger logger = LoggerFactory.getLogger(ProjectTopicServiceImpl.class);

    @Override
    public List<ProjectTopicDTO> findAll() {
        return projectTopicMapper.toDTO(projectTopicRepository.findAll());
    }

    @Override
    public ProjectTopicDTO findById(Long id) {
        return projectTopicMapper.toDTO(projectTopicRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public ProjectTopicDTO save(Long studentId, ProjectTopicDTO projectTopicDTO) {
        ProjectTopic projectTopic = projectTopicMapper.toEntity(projectTopicDTO);

        // đảm bảo có ID hợp lệ
        ProjectTopic savedTopic = projectTopicRepository.save(projectTopic);
        projectTopicRepository.flush(); // 👈 bắt Hibernate insert ngay

        logger.info("pass here = {}", studentId);

        Student student = studentRepository.findByUserId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        logger.info("-----------Student found: {}", student);

        StudentProject sp = new StudentProject();
        sp.setStudent(student);
        sp.setProject(savedTopic);
        sp.setIsApproved(false);
        sp.setRegisterDate(LocalDate.now());

        studentProjectRepository.save(sp);

        return projectTopicMapper.toDTO(savedTopic);
    }

    @Override
    public void deleteById(Long id) {
        projectTopicRepository.deleteById(id);
    }

    @Override
    public Page<ProjectTopicFullDto> getFullTopicInfoWithStudentFilter(
            Long teacherId, String title, String studentCode, Pageable pageable) {

        String titleFilter = (title == null || title.isBlank()) ? "%" : "%" + title + "%";
        String studentCodeFilter = (studentCode == null || studentCode.isBlank()) ? "%" : "%" + studentCode + "%";

        return projectTopicRepository.findFullTopicInfoWithStudentFilter(
                teacherId, titleFilter, studentCodeFilter, pageable);
    }

}
