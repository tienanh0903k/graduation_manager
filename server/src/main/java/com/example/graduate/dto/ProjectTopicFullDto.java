package com.example.graduate.dto;

public record ProjectTopicFullDto(
    Long id,
    String title,
    String description,
    String teacherName,
    String councilName,
    Long reviewerCount
) {}