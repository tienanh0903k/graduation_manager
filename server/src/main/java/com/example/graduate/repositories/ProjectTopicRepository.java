package com.example.graduate.repositories;

import com.example.graduate.dto.ProjectTopicDTO;
import com.example.graduate.dto.ProjectTopicFullDto;
import com.example.graduate.models.ProjectTopic;
import com.example.graduate.repositories.queries.ProjectTopicQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectTopicRepository extends JpaRepository<ProjectTopic, Long> {

  @Query(ProjectTopicQuery.SELECT_TOPIC_BY_TEACHER_ID)
  Page<ProjectTopicFullDto> findFullTopicInfoWithStudentFilter(
      @Param("teacherId") Long teacherId,
      @Param("title") String title,
      @Param("studentCode") String studentCode,
      Pageable pageable);

}
