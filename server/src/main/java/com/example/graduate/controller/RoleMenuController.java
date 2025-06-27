package com.example.graduate.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.graduate.dto.MenuPermission.MenuPermissionDTO;
import com.example.graduate.models.RoleName;
import com.example.graduate.service.interfaces.IRoleMenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class RoleMenuController {
    private final IRoleMenuService roleMenuService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
     @PostMapping("/{roleName}/assign-menus")
     public ResponseEntity<String> assignMenusToRole(
          @PathVariable("roleName") RoleName roleName,
          @RequestBody List<MenuPermissionDTO> menuPermissions) {
            try {
                roleMenuService.assignMenusToRole(roleName, menuPermissions);
                return ResponseEntity.ok("Menus assigned successfully to role: " + roleName);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error assigning menus to role: " + e.getMessage());
            }
     }


}
