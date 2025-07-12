package com.example.graduate.dto.Students;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class StudentDTO {
    private String mssv;
    private String name;
    private String email;
    private String password;
    private String className;

    // Nếu cần thiết, @JsonCreator có thể thêm để làm rõ ánh xạ
    @JsonCreator
    public StudentDTO(
            @JsonProperty("mssv") String mssv,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("className") String className) {
        this.mssv = mssv;
        this.name = name;
        this.email = email;
        this.password = password;
        this.className = className;
    }
}
