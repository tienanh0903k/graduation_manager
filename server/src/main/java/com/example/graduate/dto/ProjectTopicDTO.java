package com.example.graduate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTopicDTO {
        private Long id;
        private String title;
        private String description;
        private Long teacherId;
        private Integer maxSlots;
        private Long defenseCouncilId;
        private List<Long> reviewerIds;
}
