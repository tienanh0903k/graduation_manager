package com.example.graduate.dto;

import lombok.Data;

@Data
public class AssignRoleRequest {
    private Long userId;
    private Long roleId;
}
