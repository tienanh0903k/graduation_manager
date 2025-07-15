package com.example.graduate.dto.StudentProject;


public record StudentProjectListDTO(
    Long id,
    String tenGiaoVien,
    String tenDeTai,
    String tenSinhVien,
    String maSinhVien,
    Long userId,
    String lop,
    Boolean trangThai,
    String reviewerName
) {
}