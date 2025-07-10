package com.example.graduate.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.graduate.dto.TeacherDTO;
import com.example.graduate.mapper.UserMapper;
import com.example.graduate.models.RoleName;
import com.example.graduate.repositories.UserRepository;
import com.example.graduate.service.interfaces.ITeacherService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements ITeacherService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;

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
}