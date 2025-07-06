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
@Table(name = "council_members")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouncilMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "council_id")
    private Council council;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher lecture;

    @Column(length = 30)
    private String role; // chủ tịch, thư ký, ủy viên
}
