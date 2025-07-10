package com.example.graduate.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.graduate.dto.MenuPermission.MenuPermissionDTO;
import com.example.graduate.models.MenuItem;
import com.example.graduate.models.RoleMenuPermission;
import com.example.graduate.models.RoleName;
import com.example.graduate.models.Roles;
import com.example.graduate.repositories.MenuItemRepository;
import com.example.graduate.repositories.RoleMenuPermissionRepository;
import com.example.graduate.repositories.RoleRepository;
import com.example.graduate.service.interfaces.IRoleMenuService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@Transactional
@Service
@RequiredArgsConstructor
public class RoleMenuService implements IRoleMenuService {

        private final RoleRepository roleRepository;
        private final MenuItemRepository menuItemRepository;
        private final RoleMenuPermissionRepository roleMenuPermissionRepository;

        @Autowired
        private final ModelMapper modelMapper;

        // @Override
        // public void assignMenusToRole(RoleName roleName, List<MenuPermissionDTO>
        // menuPermissions) {
        // // Tìm role theo tên
        // Roles role = roleRepository.findByName(roleName)
        // .orElseThrow(() -> new RuntimeException("Role not found"));

        // // Xóa tất cả menu đã gán trước đó cho role
        // roleMenuPermissionRepository.deleteByRole(role);

        // // Gán menu mới cho role
        // List<RoleMenuPermission> roleMenuPermissions = menuPermissions.stream()
        // .map(menuPermissionDTO -> {
        // // Tìm MenuItem từ menuId
        // MenuItem menuItem =
        // menuItemRepository.findById(menuPermissionDTO.getMenuId())
        // .orElseThrow(
        // () -> new RuntimeException("Menu not found: " +
        // menuPermissionDTO.getMenuId()));

        // // Tạo và trả về RoleMenuPermission với quyền từ MenuPermissionDTO
        // return RoleMenuPermission.builder()
        // .role(role)
        // .menu(menuItem) // Gán MenuItem vào menu
        // .canRead(menuPermissionDTO.isCanRead())
        // .canCreate(menuPermissionDTO.isCanCreate())
        // .canUpdate(menuPermissionDTO.isCanUpdate())
        // .canDelete(menuPermissionDTO.isCanDelete())
        // .build();
        // })
        // .collect(Collectors.toList());

        // // Lưu các quyền mới vào cơ sở dữ liệu
        // roleMenuPermissionRepository.saveAll(roleMenuPermissions);
        // }

        @Override
        public void assignMenusToRole(RoleName roleName, List<MenuPermissionDTO> menuPermissions) {
                Roles role = roleRepository.findByName(roleName)
                                .orElseThrow(() -> new RuntimeException("Role not found"));
                roleMenuPermissionRepository.deleteByRole(role);

                List<RoleMenuPermission> roleMenuPermissions = menuPermissions.stream()
                                .map(menuPermissionDTO -> {
                                        MenuItem menuItem = menuItemRepository.findById(menuPermissionDTO.getMenuId())
                                                        .orElseThrow(
                                                                        () -> new RuntimeException("Menu not found: "
                                                                                        + menuPermissionDTO
                                                                                                        .getMenuId()));

                                        RoleMenuPermission roleMenuPermission = modelMapper.map(menuPermissionDTO,
                                                        RoleMenuPermission.class);

                                        roleMenuPermission.setRole(role);
                                        roleMenuPermission.setMenu(menuItem);

                                        return roleMenuPermission;
                                })
                                .collect(Collectors.toList());

                roleMenuPermissionRepository.saveAll(roleMenuPermissions);
        }

        @Override
        public Map<String, List<MenuItem>> getMenusByRole(RoleName roleName) {
                Roles role = roleRepository.findByName(roleName)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy role"));

                Long roleId = role.getId();

                List<MenuItem> assigned = menuItemRepository.findAssignedMenusByRoleId(roleId);
                List<MenuItem> unassigned = menuItemRepository.findUnassignedMenusByRoleId(roleId);

                Map<String, List<MenuItem>> result = new HashMap<>();
                result.put("assigned", assigned);
                result.put("unassigned", unassigned);
                return result;
        }

}
