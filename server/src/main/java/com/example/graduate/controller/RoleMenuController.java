package com.example.graduate.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.graduate.dto.MenuItem.Request.MenuIdsRequest;
import com.example.graduate.dto.MenuPermission.MenuPermissionDTO;
import com.example.graduate.models.MenuItem;
import com.example.graduate.models.RoleName;
import com.example.graduate.response.ResponseObject;
import com.example.graduate.service.interfaces.IRoleMenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class RoleMenuController {
    private final IRoleMenuService roleMenuService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/{roleName}/assign-menus")
    public ResponseEntity<ResponseObject> assignMenusToRole(
            @PathVariable("roleName") RoleName roleName,
            @RequestBody List<MenuPermissionDTO> menuPermissions) {

        roleMenuService.assignMenusToRole(roleName, menuPermissions);
        return ResponseEntity.ok(
                new ResponseObject("Menus assigned successfully", HttpStatus.OK, null));
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String testAdmin() {
        return "Bạn là admin!";
    }

    @PutMapping("/{roleName}/menus")
    public ResponseEntity<?> updateMenusForRole(
            @PathVariable RoleName roleName,
            @RequestBody MenuIdsRequest request) {
        roleMenuService.assignMenusToRoleSimple(roleName, request.getMenuIds());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{roleName}/menus")
    public ResponseEntity<?> getMenusForRole(@PathVariable RoleName roleName) {
        Map<String, List<MenuItem>> data = roleMenuService.getMenusByRole(roleName);
        return ResponseEntity.ok(data);
    }

}
