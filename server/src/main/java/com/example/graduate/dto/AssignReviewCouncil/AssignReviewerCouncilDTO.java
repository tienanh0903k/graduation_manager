package com.example.graduate.dto.AssignReviewCouncil;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignReviewerCouncilDTO {
    private List<Long> projectIds;
    private List<Long> reviewerId;
    private Long councilId;

}
