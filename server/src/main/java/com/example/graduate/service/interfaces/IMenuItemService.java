package com.example.graduate.service.interfaces;

import com.example.graduate.dto.MenuItem.MenuItemDTO;
import com.example.graduate.models.MenuItem;
import java.util.List;

public interface IMenuItemService {

    // Lấy danh sách tất cả MenuItem
    List<MenuItemDTO> getAllMenuItems();

    // Tạo mới một MenuItem
    MenuItemDTO createMenuItem(MenuItemDTO menuItem) throws RuntimeException;

    // Cập nhật một MenuItem
    MenuItem updateMenuItem(Long id, MenuItem menuItem);

    // Xóa một MenuItem
    void deleteMenuItem(Long id);

    // Lấy một MenuItem theo ID
    MenuItem getMenuItemById(Long id);
}
