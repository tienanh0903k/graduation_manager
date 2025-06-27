package com.example.graduate.repositories;

import org.springframework.stereotype.Repository;

import com.example.graduate.models.RoleMenuPermission;
import com.example.graduate.models.Roles;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleMenuPermissionRepository extends JpaRepository<RoleMenuPermission, Long> {
    /**
     * Xóa tất cả menu đã gán cho role cụ thể.
     *
     * @param roleMenuPermission RoleMenuPermission chứa thông tin role
     */
    void deleteByRole(Roles role);
    
}
