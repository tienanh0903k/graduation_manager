package com.example.graduate.repositories.queries;

public interface ProjectTopicQuery {
    String SELECT_TOPIC_BY_TEACHER_ID = """
        SELECT new com.example.graduate.dto.ProjectTopicFullDto(
            pt.id,
            pt.title,
            pt.description,
            u.name,
            c.name,
            COUNT(DISTINCT r)
        )
        FROM ProjectTopic pt
        LEFT JOIN pt.teacher t
        LEFT JOIN t.user u
        LEFT JOIN pt.defenseCouncil c
        LEFT JOIN pt.reviewers r
        LEFT JOIN StudentProject sp ON sp.project.id = pt.id
        LEFT JOIN sp.student s
        WHERE (:teacherId IS NULL OR t.id = :teacherId)
          AND (:title IS NULL OR LOWER(pt.title) LIKE LOWER(CONCAT('%', :title, '%')))
          AND (:studentCode IS NULL OR s.mssv LIKE CONCAT('%', :studentCode, '%'))
        GROUP BY pt.id, pt.title, pt.description, u.name, c.name
        """;
}
