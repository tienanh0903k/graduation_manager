package com.example.graduate.service.interfaces;

import com.example.graduate.dto.ProjectTopicDTO;

import java.util.List;

public interface IProjectTopicService {
    List<ProjectTopicDTO> findAll();
    ProjectTopicDTO findById(Long id);
    ProjectTopicDTO save(Long studentId, ProjectTopicDTO projectTopicDTO);
    void deleteById(Long id);
}
