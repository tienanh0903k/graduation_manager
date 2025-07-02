package com.example.graduate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.graduate.models.StudentProject;

@Repository

public interface StudentProjectRepository extends JpaRepository<StudentProject, Long> {


    
}
