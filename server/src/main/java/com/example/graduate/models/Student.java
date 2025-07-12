package com.example.graduate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data          
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String mssv;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    //example : 125213
    @Column(name = "class_name")
    private String classCode;
}
