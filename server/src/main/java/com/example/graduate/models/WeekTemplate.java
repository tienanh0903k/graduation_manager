package com.example.graduate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "week_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeekTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //term_id
    @ManyToOne
    @JoinColumn(name = "project_term_id", nullable = false)
    private ProjectTerm projectTerm;

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @Column(length = 255)
    private String task;

    @Column(columnDefinition = "TEXT")
    private String guide;
}
