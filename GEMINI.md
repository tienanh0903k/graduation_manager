//Viết phương thức lấy danh sách menu + quyền theo user

// service/impl/UserServiceImpl.java (hoặc nơi nào bạn xử lý login)
public List<MenuPermissionResponseDTO> getMenusWithPermissionsByUser(Users user) {
    Roles role = user.getRole(); // Lấy role của user

    List<RoleMenuPermission> roleMenuPermissions = roleMenuPermissionRepository.findByRole(role);

    return roleMenuPermissions.stream().map(rmp -> {
        MenuItem menu = rmp.getMenu();

        return MenuPermissionResponseDTO.builder()
            .label(menu.getLabel())
            .route(menu.getRoute())
            .icon(menu.getIcon())
            .permissions(List.of(PermissionDTO.builder()
                .canRead(rmp.isCanRead())
                .canCreate(rmp.isCanCreate())
                .canUpdate(rmp.isCanUpdate())
                .canDelete(rmp.isCanDelete())
                .build()))
            .build();
    }).collect(Collectors.toList());
}