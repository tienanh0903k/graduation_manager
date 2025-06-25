package com.example.graduate.service.impl;

import com.example.graduate.dto.MenuItem.MenuItemDTO;
import com.example.graduate.mapper.MenuItem.MenuItemMapper;
import com.example.graduate.models.MenuItem;
import com.example.graduate.repositories.MenuItemRepository;
import com.example.graduate.service.interfaces.IMenuItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements IMenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<MenuItemDTO> getAllMenuItems() {
         return menuItemRepository.findAll()
            .stream()
            .map(MenuItemMapper::toDto)
            .collect(Collectors.toList());
    }


    // The method createMenuItem(MenuItemDTO) of type MenuItemServiceImpl must override or implement a supertype methodJava(67109498)
    @Override
    public MenuItemDTO createMenuItem(MenuItemDTO dto) throws RuntimeException {
        MenuItem parent = null;
        if (dto.getParentId() != null) {
            parent = menuItemRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent not found"));
        }

        // Sử dụng Builder để tạo đối tượng MenuItem
        MenuItem menuItem = MenuItem.builder()
                .label(dto.getLabel())
                .route(dto.getRoute())
                .icon(dto.getIcon())
                .orderNo(dto.getOrderNo())
                .isVisible(dto.getIsVisible())
                .parent(parent) // Gán parent nếu có
                .build();

        // Lưu MenuItem mới vào cơ sở dữ liệu
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        // Chuyển đổi sang DTO và trả về
        return MenuItemMapper.toDto(savedMenuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        Optional<MenuItem> existingMenuItem = menuItemRepository.findById(id);
        if (existingMenuItem.isPresent()) {
            MenuItem updatedMenuItem = existingMenuItem.get();
            updatedMenuItem.setLabel(menuItem.getLabel());
            updatedMenuItem.setRoute(menuItem.getRoute());
            updatedMenuItem.setIcon(menuItem.getIcon());
            return menuItemRepository.save(updatedMenuItem);  // Lưu bản cập nhật vào cơ sở dữ liệu
        }
        return null;  // Nếu không tìm thấy MenuItem, trả về null
    }

    @Override
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);  // Xóa MenuItem khỏi cơ sở dữ liệu
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElse(null);  // Tìm MenuItem theo ID, nếu không tìm thấy trả về null
    }
}
