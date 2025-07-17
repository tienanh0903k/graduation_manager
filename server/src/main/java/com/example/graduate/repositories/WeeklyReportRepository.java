package com.example.graduate.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.graduate.dto.WeeklyReport.StudentWeeklyReportDTO;
import com.example.graduate.models.WeeklyReport;

public interface WeeklyReportRepository extends CrudRepository<WeeklyReport, Long> {
    @Query(value = """
            SELECT
                sp.id AS student_project_id,
                pt.title AS project_title,
                u.name AS student_name,
                s.mssv AS student_code,
                s.class_name,
                wr.week_number,
                wr.start_date,
                wr.end_date,
                wr.task,
                wr.guide,
                wr.content,
                wr.comment,
                wr.score,
                wr.file_link,
                t_user.name AS teacher_name
            FROM weekly_reports wr
            JOIN student_projects sp ON wr.student_project_id = sp.id
            JOIN students s ON sp.student_id = s.id
            JOIN users u ON s.user_id = u.id
            JOIN project_topics pt ON sp.project_id = pt.id
            JOIN teachers t ON pt.teacher_id = t.id
            JOIN users t_user ON t.user_id = t_user.id
            WHERE wr.start_date <= :date
              AND u.id = :userId
            ORDER BY wr.week_number DESC
            """, 
            nativeQuery = true
    )
    List<StudentWeeklyReportDTO> findWeeklyReportsByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("date") LocalDate date);
}
