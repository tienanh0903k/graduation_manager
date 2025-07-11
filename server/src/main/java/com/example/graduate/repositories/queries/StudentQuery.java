package com.example.graduate.repositories.queries;

public interface StudentQuery {
  String SEARCH_PROJECT_BY_STUDENT = """
          SELECT new com.example.graduate.dto.StudentProject.StudentProjectListDTO(
              tu.name,
              pt.title,
              su.name,
              s.mssv,
              su.id,
              s.classCode,
              sp.isApproved
          )
          FROM ProjectTopic pt
          JOIN pt.teacher t
          JOIN t.user tu
          JOIN pt.studentProjects sp
          JOIN sp.student s
          JOIN s.user su
          WHERE (:classCode IS NULL OR LOWER(s.classCode) LIKE :classCode)
            AND (:teacherName IS NULL OR LOWER(tu.name) LIKE :teacherName)
            AND (:title IS NULL OR LOWER(pt.title) LIKE :title)
          ORDER BY tu.name, pt.title
      """;

}
