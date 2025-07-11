package com.example.graduate.controller;

import com.example.graduate.dto.ProjectTopicDTO;
import com.example.graduate.dto.ProjectTopicFullDto;
import com.example.graduate.service.interfaces.IAuthenticationService;
import com.example.graduate.service.interfaces.IProjectTopicService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/project-topics")
public class ProjectTopicController {

    private final IProjectTopicService projectTopicService;
    private final IAuthenticationService authHelper;

    @GetMapping
    public ResponseEntity<List<ProjectTopicDTO>> getAllProjectTopics() {
        return ResponseEntity.ok(projectTopicService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectTopicDTO> getProjectTopicById(@PathVariable Long id) {
        ProjectTopicDTO projectTopicDTO = projectTopicService.findById(id);
        return projectTopicDTO != null ? ResponseEntity.ok(projectTopicDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProjectTopicDTO> createProjectTopic(@RequestBody ProjectTopicDTO projectTopicDTO) {

        Long studentId = authHelper.getCurrentUserId();
        // The method save(Long, ProjectTopicDTO) in the type IProjectTopicService is
        // not applicable for the arguments (ProjectTopicDTO, Long)Java(67108979)

        ProjectTopicDTO createdTopic = projectTopicService.save(studentId, projectTopicDTO);
        return ResponseEntity.ok(createdTopic);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<ProjectTopicDTO> updateProjectTopic(@PathVariable Long
    // id, @RequestBody ProjectTopicDTO projectTopicDTO) {
    // projectTopicDTO.setId(id);
    // return ResponseEntity.ok(projectTopicService.save(projectTopicDTO));
    // }

    @GetMapping("/teacher")
    public Page<ProjectTopicFullDto> getFullTopicInfo(
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String studentCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return projectTopicService.getFullTopicInfoWithStudentFilter(teacherId, title, studentCode, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectTopic(@PathVariable Long id) {
        projectTopicService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
