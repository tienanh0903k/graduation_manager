package com.example.graduate.service.interfaces;

import com.example.graduate.dto.MenuPermission.MenuPermissionDTO;
import com.example.graduate.models.MenuItem;
import com.example.graduate.models.RoleName;


import java.util.List;
import java.util.Map;

public interface IRoleMenuService {
    /**
     * Gán menu cho role cụ thể.
     *
     * @param roleName      Tên role (ví dụ: ADMIN, USER, STUDENT)
     * @param menuItemIds   Danh sách ID của menu cần gán cho role
     */
    void assignMenusToRole(RoleName roleName, List<MenuPermissionDTO> menuPermissions);
    
    // /**
    //  * Lấy danh sách menu đã được gán cho role cụ thể.
    //  *
    //  * @param roleName  Tên role
    //  * @return          Danh sách menu đã được gán cho role
    //  */
    // List<Long> getMenusByRole(RoleName roleName);
    Map<String, List<MenuItem>> getMenusByRole(RoleName roleName);
}
