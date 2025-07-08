package com.example.graduate.repositories.queries;

public interface StudentQuery {
    String SEARCH_PROJECT_BY_STUDENT = """
        SELECT new com.example.graduate.dto.StudentProject.StudentProjectListDTO(
            tu.name,
            pt.title,
            su.name,
            s.mssv,
            s.classCode
        )
        FROM ProjectTopic pt
        JOIN pt.teacher t
        JOIN t.user tu
        JOIN pt.studentProjects sp
        JOIN sp.student s
        JOIN s.user su
        WHERE LOWER(tu.name) LIKE LOWER(CONCAT('%', :search, '%'))
           OR LOWER(pt.title) LIKE LOWER(CONCAT('%', :search, '%'))
           OR LOWER(s.classCode) LIKE LOWER(CONCAT('%', :search, '%'))
        ORDER BY tu.name, pt.title
    """;
}
