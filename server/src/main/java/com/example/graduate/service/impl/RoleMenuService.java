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

                public void assignMenusToRoleSimple(RoleName roleName, List<Long> menuIds) {
                        Roles role = roleRepository.findByName(roleName)
                                        .orElseThrow(() -> new RuntimeException("Role not found"));

                        roleMenuPermissionRepository.deleteByRole(role);

                        List<MenuItem> menuItems = menuItemRepository.findAllById(menuIds);

                        List<RoleMenuPermission> permissions = menuItems.stream()
                                        .map(menu -> {
                                                RoleMenuPermission perm = new RoleMenuPermission();
                                                perm.setRole(role);
                                                perm.setMenu(menu);
                                                perm.setCanRead(true);
                                                perm.setCanCreate(true);
                                                perm.setCanUpdate(true);
                                                perm.setCanDelete(true);
                                                return perm;
                                        })
                                        .collect(Collectors.toList());

                        roleMenuPermissionRepository.saveAll(permissions);
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
