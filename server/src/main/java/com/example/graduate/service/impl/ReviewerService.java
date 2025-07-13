package com.example.graduate.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.graduate.models.Council;
import com.example.graduate.models.ProjectTopic;
import com.example.graduate.models.Reviewer;
import com.example.graduate.models.Teacher;
import com.example.graduate.repositories.ProjectTopicRepository;
import com.example.graduate.repositories.ReviewerRepository;
import com.example.graduate.repositories.TeacherRepository;
import com.example.graduate.service.interfaces.IReviewerService;

@Service
public class ReviewerService implements IReviewerService {
    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ProjectTopicRepository projectTopicRepository;

    @Override
    public void assignReviewersAndCouncil(List<Long> projectIds, List<Long> reviewerUserIds, Long councilId) {
        // Convert reviewerUserIds sang reviewerIds (là teacherId)
        List<Long> reviewerIds = reviewerUserIds.stream()
                .map(userId -> {
                    Teacher teacher = teacherRepository.findByUserId(userId);
                    if (teacher == null) {
                        throw new IllegalArgumentException("Không tìm thấy teacher cho userId = " + userId);
                    }
                    return teacher.getId();
                })
                .collect(Collectors.toList());

        // Phần còn lại giữ nguyên như cũ
        if (reviewerIds != null && !reviewerIds.isEmpty()) {
            for (Long projectId : projectIds) {
                for (Long reviewerId : reviewerIds) {
                    if (!reviewerRepository.existsByProjectIdAndTeacherId(projectId, reviewerId)) {
                        Reviewer reviewer = new Reviewer();

                        Teacher teacher = new Teacher();
                        teacher.setId(reviewerId);
                        reviewer.setTeacher(teacher);

                        ProjectTopic project = new ProjectTopic();
                        project.setId(projectId);
                        reviewer.setProject(project);

                        reviewerRepository.save(reviewer);
                    }
                }
            }
        }

        // Gán hội đồng bảo vệ (nếu có)
        if (councilId != null) {
            List<ProjectTopic> projects = projectTopicRepository.findAllById(projectIds);
            for (ProjectTopic topic : projects) {
                Council council = new Council();
                council.setId(councilId);
                topic.setDefenseCouncil(council); // set entity chứ không phải id
            }
            projectTopicRepository.saveAll(projects);
        }
    }

}
