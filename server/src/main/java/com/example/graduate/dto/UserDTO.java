package com.example.graduate.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;   // dùng chung với entity
    private String email;
    private String password;
    private String role;
}
