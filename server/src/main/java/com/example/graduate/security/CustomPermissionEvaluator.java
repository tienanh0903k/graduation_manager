package com.example.graduate.security;

import com.example.graduate.models.MenuItem;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("permissionEvaluator")
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject == null || permission == null) {
            return false;
        }

        MenuItem menuItem = (MenuItem) targetDomainObject;
        String permissionStr = (String) permission;

        // Kiểm tra quyền đối với MenuItem
        if ("canRead".equals(permissionStr)) {
            return hasReadPermission(authentication, menuItem);
        } else if ("canCreate".equals(permissionStr)) {
            return hasCreatePermission(authentication, menuItem);
        } else if ("canUpdate".equals(permissionStr)) {
            return hasUpdatePermission(authentication, menuItem);
        } else if ("canDelete".equals(permissionStr)) {
            return hasDeletePermission(authentication, menuItem);
        }

        return false;
    }

    private boolean hasReadPermission(Authentication authentication, MenuItem menuItem) {
        return checkPermission(authentication, menuItem, "canRead");
    }

    private boolean hasCreatePermission(Authentication authentication, MenuItem menuItem) {
        return checkPermission(authentication, menuItem, "canCreate");
    }

    private boolean hasUpdatePermission(Authentication authentication, MenuItem menuItem) {
        return checkPermission(authentication, menuItem, "canUpdate");
    }

    private boolean hasDeletePermission(Authentication authentication, MenuItem menuItem) {
        return checkPermission(authentication, menuItem, "canDelete");
    }

    private boolean checkPermission(Authentication authentication, MenuItem menuItem, String permissionType) {
        // Kiểm tra quyền đối với MenuItem dựa trên role của người dùng
        // Trong ví dụ này, bạn có thể kiểm tra quyền của người dùng đối với từng menu item
        // Bạn có thể tùy chỉnh logic này theo yêu cầu cụ thể của bạn

        // Lấy thông tin role của người dùng
        String userRole = authentication.getAuthorities().toString();
        if ("ADMIN".equals(userRole)) {
            return true; // Admin có quyền toàn quyền
        }

        // Kiểm tra quyền dựa trên quyền của người dùng đối với menuItem
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false; // Không cần sử dụng trong trường hợp này
    }
}
