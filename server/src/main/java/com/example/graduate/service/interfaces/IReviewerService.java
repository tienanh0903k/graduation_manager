package com.example.graduate.service.interfaces;

import java.util.List;

public interface IReviewerService {
    // void assignReviewers(List<Long> projectIds, List<Long> reviewerIds);
    // void assignReviewer(Long projectId, Long reviewerId);
    void assignReviewersAndCouncil(List<Long> projectIds, List<Long> reviewerIds, Long councilId);
}
