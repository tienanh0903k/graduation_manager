package com.example.graduate.dto.StudentProject;

public record StudentProjectListDTO(
        String tenGiaoVien,
        String tenDeTai,
        String tenSinhVien,
        String maSinhVien,
        Long id,
        String lop,
        Boolean trangThai
        ) {
}