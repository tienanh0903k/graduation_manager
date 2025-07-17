package com.example.graduate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.graduate.dto.WeeklyReport.StudentWeeklyReportDTO;
import com.example.graduate.service.impl.WeeklyReportServiceImpl;
import com.example.graduate.service.interfaces.IAuthenticationService;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reports")
public class WeeklyReportController {

    private static final Logger logger = LoggerFactory.getLogger(WeeklyReportController.class);

    private final WeeklyReportServiceImpl weeklyReportService;

    private final IAuthenticationService authHelper;

    @GetMapping("/user")
    public List<StudentWeeklyReportDTO> getWeeklyReports(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        //get current 
        Long userId = authHelper.getCurrentUserId();
        logger.info("Fetching weekly reports for user ID: {}, date: {}", userId, date);
        return weeklyReportService.getWeeklyReports(userId, date);
    }
}