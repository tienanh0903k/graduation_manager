package com.example.graduate.controller;

import com.example.graduate.dto.MenuItem.MenuItemDTO;
import com.example.graduate.models.MenuItem;
import com.example.graduate.response.Response;
import com.example.graduate.service.interfaces.IMenuItemService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final IMenuItemService menuItemService;

    public MenuController(IMenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }
    /**
     * Lấy danh sách tất cả các MenuItem.
     *
     * @return Danh sách MenuItemDTO.
     */
    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }
    /**
     * Lấy danh sách tất cả các MenuItem.
     *
     * @return Danh sách MenuItemDTO.
     */
    @PostMapping
    public ResponseEntity<Response<MenuItemDTO>> createMenuItem(@Valid @RequestBody MenuItemDTO menuItem) {
        MenuItemDTO createdMenu = menuItemService.createMenuItem(menuItem);
        return ResponseEntity.ok(
            Response.success("Tạo thành công MenuItem", createdMenu)
        );
    }

    /**
     * Lấy danh sách tất cả các MenuItem.
     *
     * @return Danh sách MenuItemDTO.
     */
    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(id, menuItem);
    }
    /**
     * Lấy danh sách tất cả các MenuItem.
     *
     * @return Danh sách MenuItemDTO.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
    }
    /**
     * Lấy danh sách tất cả các MenuItem.
     *
     * @return Danh sách MenuItemDTO.
     */
    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }
}
