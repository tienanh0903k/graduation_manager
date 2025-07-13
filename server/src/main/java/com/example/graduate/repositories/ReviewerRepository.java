package com.example.graduate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.graduate.models.Reviewer;

public interface ReviewerRepository  extends JpaRepository<Reviewer, Long> {
    boolean existsByProjectIdAndTeacherId(Long projectId, Long teacherId);
}
