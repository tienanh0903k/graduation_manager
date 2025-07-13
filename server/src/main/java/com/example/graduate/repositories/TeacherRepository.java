package com.example.graduate.repositories;

import com.example.graduate.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Long findIdByUserId(Long userId);

    Teacher findByUserId(Long userId);

}