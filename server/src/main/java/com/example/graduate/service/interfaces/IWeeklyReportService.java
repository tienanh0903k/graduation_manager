package com.example.graduate.service.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.example.graduate.dto.WeeklyReport.StudentWeeklyReportDTO;

public interface IWeeklyReportService {
      List<StudentWeeklyReportDTO> getWeeklyReports(Long userId, LocalDate date);
}
