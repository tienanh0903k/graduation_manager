package com.example.graduate.repositories;

import com.example.graduate.models.MenuItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// MenuItemRepository kế thừa JpaRepository để dễ dàng tương tác với cơ sở dữ liệu
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByLabel(String label);

    @Query("SELECT m FROM MenuItem m WHERE m.id IN " +
            "(SELECT p.menu.id FROM RoleMenuPermission p WHERE p.role.id = :roleId)")
    List<MenuItem> findAssignedMenusByRoleId(@Param("roleId") Long roleId);


     @Query("SELECT m FROM MenuItem m WHERE m.id NOT IN " +
           "(SELECT p.menu.id FROM RoleMenuPermission p WHERE p.role.id = :roleId)")
    List<MenuItem> findUnassignedMenusByRoleId(@Param("roleId") Long roleId);

}
