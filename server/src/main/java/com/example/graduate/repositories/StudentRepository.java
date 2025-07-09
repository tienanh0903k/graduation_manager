package com.example.graduate.repositories;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.graduate.dto.StudentProject.StudentProjectListDTO;
import com.example.graduate.models.Student;
import com.example.graduate.repositories.queries.StudentQuery;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(Long userId);

    /**
     * get list project for role user (student)
     * using pageable
     * 
     * @param search : classCode , nameProject , nameTeacher
     */
    @Query(StudentQuery.SEARCH_PROJECT_BY_STUDENT)
    Page<StudentProjectListDTO> searchByFilters(
            @Param("classCode") String classCode,
            @Param("teacherName") String teacherName,
            @Param("title") String title,
            Pageable pageable);
}
