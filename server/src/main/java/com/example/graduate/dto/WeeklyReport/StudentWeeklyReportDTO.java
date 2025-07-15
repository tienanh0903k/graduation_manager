package com.example.graduate.dto.WeeklyReport;

// public record StudentWeeklyReportDTO(
//     Long studentProjectId,
//     String projectTitle,
//     String studentName,
//     String studentCode,
//     String className,
//     Integer weekNumber,
//     java.time.LocalDate startDate,
//     java.time.LocalDate endDate,
//     String task,
//     String guide,
//     String content,
//     String comment,
//     Float score,
//     String fileLink
// ) {
    
// }

public interface StudentWeeklyReportDTO {
    Long getStudentProjectId();
    String getProjectTitle();
    String getStudentName();
    String getStudentCode();
    String getClassName();
    Integer getWeekNumber();
    java.time.LocalDate getStartDate();
    java.time.LocalDate getEndDate();
    String getTask();
    String getGuide();
    String getContent();
    String getComment();
    Float getScore();
    String getFileLink();
}
