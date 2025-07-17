package com.example.graduate.service.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.example.graduate.dto.WeeklyReport.StudentWeeklyReportDTO;
import com.example.graduate.models.StudentProject;

public interface IWeeklyReportService {
      List<StudentWeeklyReportDTO> getWeeklyReports(Long userId, LocalDate date);

      void create12WeeklyReports(StudentProject studentProject);
}
