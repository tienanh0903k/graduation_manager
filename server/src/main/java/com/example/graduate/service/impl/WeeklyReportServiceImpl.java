package com.example.graduate.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.graduate.dto.WeeklyReport.StudentWeeklyReportDTO;
import com.example.graduate.models.ProjectTerm;
import com.example.graduate.models.StudentProject;
import com.example.graduate.models.WeekTemplate;
import com.example.graduate.models.WeeklyReport;
import com.example.graduate.repositories.WeekTemplateRepository;
import com.example.graduate.repositories.WeeklyReportRepository;
import com.example.graduate.service.interfaces.IWeeklyReportService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class WeeklyReportServiceImpl implements IWeeklyReportService {
    private final WeeklyReportRepository weeklyReportRepository;

    @Autowired
    private WeekTemplateRepository weekTemplateRepository;

    @Override
    public List<StudentWeeklyReportDTO> getWeeklyReports(Long userId, LocalDate date) {
        return weeklyReportRepository.findWeeklyReportsByUserIdAndDate(userId, date);
    }

    @Override
    public void create12WeeklyReports(StudentProject studentProject) {
        ProjectTerm term = studentProject.getTerm();

        LocalDate termStart = term.getStartDate();
        LocalDate termEnd = term.getEndDate();
        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(termStart, termEnd) + 1;
        long daysPerWeek = totalDays / 12;

        for (int i = 0; i < 12; i++) {
            int weekNumber = i + 1;
            LocalDate start = termStart.plusDays(i * daysPerWeek);
            LocalDate end = (i == 11) ? termEnd : termStart.plusDays((i + 1) * daysPerWeek - 1);

            WeekTemplate template = weekTemplateRepository
                    .findByProjectTermAndWeekNumber(term, weekNumber)
                    .orElse(new WeekTemplate());

            WeeklyReport report = new WeeklyReport();
            report.setStudentProject(studentProject);
            report.setWeekNumber(i + 1);
            report.setStartDate(start);
            report.setEndDate(end);
            report.setTask(template.getTask() != null ? template.getTask() : "Tuần " + weekNumber);
            report.setGuide(template.getGuide() != null ? template.getGuide() : "");
            report.setContent("");
            report.setFileLink(null);
            report.setScore(null);
            report.setComment("");

            weeklyReportRepository.save(report);
        }
    }

}
