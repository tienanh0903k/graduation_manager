package com.example.graduate.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.graduate.dto.WeeklyReport.StudentWeeklyReportDTO;
import com.example.graduate.repositories.WeeklyReportRepository;
import com.example.graduate.service.interfaces.IWeeklyReportService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class WeeklyReportServiceImpl implements IWeeklyReportService {
    private final WeeklyReportRepository weeklyReportRepository;


    @Override
    public List<StudentWeeklyReportDTO> getWeeklyReports(Long userId, LocalDate date) {
        return weeklyReportRepository.findWeeklyReportsByUserIdAndDate(userId, date);
    }
    

}
