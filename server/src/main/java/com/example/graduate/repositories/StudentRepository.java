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

    /**
     * check exist student by mssv
     * @param mssv
     * @return
     */
    boolean existsByMssv(String mssv);




    /**
     * Tìm kiếm sinh viên có phân trang
     * 
     * @param name      họ tên (tìm trong bảng Users)
     * @param mssv      mã số sinh viên
     * @param classCode lớp
     * @param email     email (tìm trong bảng Users)
     * @param pageable  thông tin phân trang
     * @return trang kết quả
     */
    @Query("""
             SELECT s
             FROM Student s
             JOIN s.user u
             WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')))
               AND (:mssv IS NULL OR LOWER(s.mssv) LIKE LOWER(CONCAT('%', :mssv, '%')))
               AND (:classCode IS NULL OR LOWER(s.classCode) LIKE LOWER(CONCAT('%', :classCode, '%')))
            """)
    Page<Student> searchStudents(
            @Param("name") String name,
            @Param("mssv") String mssv,
            @Param("classCode") String classCode,
            Pageable pageable);

}
