package com.example.graduate.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.graduate.models.ProjectTerm;
import com.example.graduate.models.WeekTemplate;

public interface WeekTemplateRepository extends JpaRepository<WeekTemplate, Long> {
    Optional<WeekTemplate> findByProjectTermAndWeekNumber(ProjectTerm term, Integer weekNumber);

}
