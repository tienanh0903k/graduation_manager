package com.example.graduate.mapper.MenuItem;

import com.example.graduate.dto.MenuItem.MenuItemDTO;
import com.example.graduate.dto.MenuItem.RoleMenuPermissionDTO;
import com.example.graduate.models.MenuItem;
import com.example.graduate.models.RoleMenuPermission;

import java.util.stream.Collectors;

public class MenuItemMapper {

    public static MenuItemDTO toDto(MenuItem menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setLabel(menuItem.getLabel());
        dto.setRoute(menuItem.getRoute());
        dto.setIcon(menuItem.getIcon());
        dto.setOrderNo(menuItem.getOrderNo());
        dto.setIsVisible(menuItem.getIsVisible());
        dto.setParentId(menuItem.getParent() != null ? menuItem.getParent().getId() : null);
        dto.setPermissions(menuItem.getPermissions() != null
                ? menuItem.getPermissions().stream().map(MenuItemMapper::toDto).collect(Collectors.toList())
                : null);
        return dto;
    }

    public static RoleMenuPermissionDTO toDto(RoleMenuPermission permission) {
        RoleMenuPermissionDTO dto = new RoleMenuPermissionDTO();
        dto.setId(permission.getId());
        dto.setCanRead(permission.isCanRead());
        dto.setCanCreate(permission.isCanCreate());
        dto.setCanUpdate(permission.isCanUpdate());
        dto.setCanDelete(permission.isCanDelete());
        dto.setRoleId(permission.getRole() != null ? permission.getRole().getId() : null);
        dto.setMenuId(permission.getMenu() != null ? permission.getMenu().getId() : null);
        return dto;
    }
}
