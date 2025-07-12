package com.example.graduate.dto.Students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchStudentDTO {
    private String mssv;
    private String name;
    private String email;
    private String classCode;
}