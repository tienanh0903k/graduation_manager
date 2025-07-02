package com.example.graduate.mapper;

import com.example.graduate.dto.ProjectTopicDTO;
import com.example.graduate.models.ProjectTopic;

import com.example.graduate.models.Reviewer;

import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProjectTopicMapper extends BaseMapper<ProjectTopic, ProjectTopicDTO> {

    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "defenseCouncil.id", target = "defenseCouncilId")
    @Mapping(target = "reviewerIds", expression = "java(mapReviewersToIds(entity.getReviewers()))")
    ProjectTopicDTO toDTO(ProjectTopic entity);

    @Mapping(source = "teacherId", target = "teacher.id")
    // @Mapping(source = "defenseCouncilId", target = "defenseCouncil.id")
    @Mapping(target = "reviewers", expression = "java(mapIdsToReviewers(dto.getReviewerIds()))")
    ProjectTopic toEntity(ProjectTopicDTO dto);

    // ====== Helper Methods ======

    default List<Long> mapReviewersToIds(List<Reviewer> reviewers) {
        if (reviewers == null) return null;
        return reviewers.stream()
                .map(Reviewer::getId)
                .collect(Collectors.toList());
    }

    default List<Reviewer> mapIdsToReviewers(List<Long> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Reviewer reviewer = new Reviewer();
            reviewer.setId(id);
            return reviewer;
        }).collect(Collectors.toList());
    }

    // Optional: Nếu muốn để MapStruct khỏi báo lỗi khi null
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectTopic updateProjectTopicFromDTO(ProjectTopicDTO dto, @MappingTarget ProjectTopic entity);
}
