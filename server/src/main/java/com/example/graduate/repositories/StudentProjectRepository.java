package com.example.graduate.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.graduate.models.StudentProject;

@Repository

public interface StudentProjectRepository extends JpaRepository<StudentProject, Long> {

    /* tìm theo NAME (user.name) */
    @Query("""
                SELECT sp FROM StudentProject sp
                JOIN sp.project p
                JOIN p.teacher t
                JOIN sp.student s   
                JOIN s.user u
                WHERE t.id = :teacherId
                  AND LOWER(u.name) LIKE LOWER(CONCAT('%', :kw, '%'))
            """)
    Page<StudentProject> searchByName(Long teacherId, String kw, Pageable pageable);

    @Query("""
                SELECT sp FROM StudentProject sp
                JOIN sp.project  p
                JOIN p.teacher t
                JOIN sp.student s
                WHERE t.id = :teacherId
                  AND LOWER(s.mssv) LIKE LOWER(CONCAT('%', :kw, '%'))
            """)
    Page<StudentProject> searchByMssv(Long teacherId, String kw, Pageable pageable);


    @Query("""
            SELECT sp FROM StudentProject sp
            WHERE sp.project.id = :projectId
        """)
    Optional<StudentProject> findByProjectId(Long projectId);

}
