package com.example.graduate.service.interfaces;

import com.example.graduate.dto.ProjectTopicDTO;
import com.example.graduate.dto.ProjectTopicFullDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProjectTopicService {
    List<ProjectTopicDTO> findAll();
    ProjectTopicDTO findById(Long id);
    ProjectTopicDTO save(Long studentId, ProjectTopicDTO projectTopicDTO);
    void deleteById(Long id);
    Page<ProjectTopicFullDto> getFullTopicInfoWithStudentFilter(Long teacherId, String name, String studentId, Pageable pageable);
}
