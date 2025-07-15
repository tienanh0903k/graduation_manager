package com.example.graduate.dto.Teacher;

import java.time.LocalDate;

public interface ProjectTopicByTeacher {
    Long getProjectId();

    String getTitle();

    String getDescription();

    String getStudentCode();

    String getClassName();

    String getStudentName();

    LocalDate getRegisterDate();

    Boolean getApproved();
}
