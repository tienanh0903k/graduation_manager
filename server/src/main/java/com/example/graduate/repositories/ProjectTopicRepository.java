package com.example.graduate.repositories;

import com.example.graduate.dto.ProjectTopicDTO;
import com.example.graduate.dto.ProjectTopicFullDto;
import com.example.graduate.dto.Teacher.ProjectTopicByTeacher;
import com.example.graduate.models.ProjectTopic;
import com.example.graduate.repositories.queries.ProjectTopicQuery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectTopicRepository extends JpaRepository<ProjectTopic, Long> {

  @Query(ProjectTopicQuery.SELECT_TOPIC_BY_TEACHER_ID)
  Page<ProjectTopicFullDto> findFullTopicInfoWithStudentFilter(
      @Param("teacherId") Long teacherId,
      @Param("title") String title,
      @Param("studentCode") String studentCode,
      Pageable pageable);

  /**
   * Find pending topics by teacher
   * 
   * @param teacherUserId
   * @param keyword
   * @return
   */
  @Query(value = """
          SELECT
              pt.id AS project_id,
              pt.title,
              pt.description,
              s.mssv AS student_code,
              s.class_name,
              u.name AS student_name,
              sp.register_date AS registerDate,
              sp.is_approved AS approved
          FROM project_topics pt
          JOIN student_projects sp ON pt.id = sp.project_id
          JOIN students s ON sp.student_id = s.id
          JOIN users u ON s.user_id = u.id
          WHERE pt.teacher_id IN (
              SELECT t.id
              FROM teachers t
              JOIN users tu ON t.user_id = tu.id
              WHERE tu.id = :teacherUserId
          )
          AND sp.is_approved = :isApproved
          AND (
              LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR s.mssv LIKE CONCAT('%', :keyword, '%')
          )
          ORDER BY u.name
      """, nativeQuery = true)
  List<ProjectTopicByTeacher> findTopicsByTeacherAndApprovalStatus(
      @Param("teacherUserId") Long teacherUserId,
      @Param("keyword") String keyword,
      @Param("isApproved") boolean isApproved);
}
