// package com.example.graduate.mapper;

// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;

// import com.example.graduate.dto.StudentProjectDTO;
// import com.example.graduate.models.StudentProject;

// @Mapper(componentModel = "spring")
// public interface StudentProjectMapper extends BaseMapper<StudentProject, StudentProjectDTO> {
//     @Mapping(source = "student.user.name", target = "studentName")
//     @Mapping(source = "student.mssv", target = "mssv")
//     @Mapping(source = "project.title", target = "projectTitle")
//     @Mapping(source = "project.description", target = "projectDescription")
//     @Mapping(source = "project.teacher.user.name", target = "teacherName")
//     StudentProjectDTO toDto(StudentProject entity);

// }
