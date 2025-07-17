package com.example.graduate.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.graduate.dto.TeacherDTO;
import com.example.graduate.dto.Teacher.ProjectTopicByTeacher;
import com.example.graduate.mapper.UserMapper;
import com.example.graduate.models.RoleName;
import com.example.graduate.models.StudentProject;
import com.example.graduate.repositories.ProjectTopicRepository;
import com.example.graduate.repositories.StudentProjectRepository;
import com.example.graduate.repositories.UserRepository;
import com.example.graduate.service.interfaces.ITeacherService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements ITeacherService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private ProjectTopicRepository projectTopicRepository;

    @Autowired
    private StudentProjectRepository studentProjectRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeeklyReportServiceImpl weeklyReportService;

    @Override
    public List<TeacherDTO> getAllTeachers() {
        logger.info("Fetching all teachers");

        return userRepository.findAll().stream()
            //    .peek(user -> {
            //     if (user.getRole() != null) {
            //         logger.info("User ID: {}, Role: {}", user.getId(), user.getRole().getName());
            //     } else {
            //         logger.debug("User ID: {}, Role: null", user.getId());
            //     }
            // })
                .filter(user -> RoleName.TEACHER.equals(user.getRole().getName()))
                .map(userMapper::toTeacherDTO)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public List<ProjectTopicByTeacher> getPendingTopicsByTeacher(Long teacherUserId, String keyword, boolean isApproved) {
        return projectTopicRepository.findTopicsByTeacherAndApprovalStatus(teacherUserId, keyword, isApproved);
    }


    @Override
    @Transactional
    public boolean approveProject(Long projectId) {

       Optional<StudentProject> projectOpt = studentProjectRepository.findByProjectId(projectId); 

        if (projectOpt.isPresent()) {
            StudentProject studentProject = projectOpt.get();
            studentProject.setIsApproved(true); // Set the project status to approved
            studentProjectRepository.save(studentProject); // Save the updated project


            weeklyReportService.create12WeeklyReports(studentProject);
            logger.info("Project with ID {} has been approved.", projectId);
            return true;
        } else {
            logger.error("Project with ID {} not found.", projectId);
            return false;
        }
    }
}