package com.example.graduate.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentProjectDTO {
    private Long id;

    private String studentName;
    private String mssv;

    private String projectTitle;
    private String projectDescription;

    private Boolean isApproved;
    private LocalDate registerDate;

    private String teacherName;
}
