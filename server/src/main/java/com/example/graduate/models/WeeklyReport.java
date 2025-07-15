package com.example.graduate.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weekly_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_project_id")
    private StudentProject studentProject;

    private Integer weekNumber;
    private LocalDate startDate;
    private LocalDate endDate;


    @Column(length = 255)
    private String task;

    @Column(columnDefinition = "TEXT")
    private String guide;



    @Column(columnDefinition = "TEXT")
    private String content;

    private String fileLink;
    private Float score;

    @Column(columnDefinition = "TEXT")
    private String comment;
}
