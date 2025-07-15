package com.example.graduate.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.graduate.models.ProjectTerm;

public interface ProjectTermRepository extends JpaRepository<ProjectTerm, Long> {
    @Query("SELECT t FROM ProjectTerm t WHERE :date BETWEEN t.startDate AND t.endDate")
    Optional<ProjectTerm> findByDateBetween(@Param("date") LocalDate date);
}
