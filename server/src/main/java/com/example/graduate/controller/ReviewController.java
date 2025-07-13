package com.example.graduate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.graduate.dto.AssignReviewCouncil.AssignReviewerCouncilDTO;
import com.example.graduate.service.interfaces.IReviewerService;

@RestController
@RequestMapping("/api/reviewer")
public class ReviewController {
    @Autowired
    private IReviewerService reviewerService;

    @PostMapping("/assign-reviewers-council")
    public ResponseEntity<?> assignReviewersAndCouncil(@RequestBody AssignReviewerCouncilDTO req) {
        reviewerService.assignReviewersAndCouncil(
                req.getProjectIds(),
                req.getReviewerId(),
                req.getCouncilId());
        return ResponseEntity.ok().build();
    }

}
