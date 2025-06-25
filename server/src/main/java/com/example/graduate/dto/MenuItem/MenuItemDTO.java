package com.example.graduate.dto.MenuItem;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {
    private Long id;
    @NotNull(message = "Label cannot be null")
    @Size(min = 1, max = 100, message = "Label must be between 1 and 100 characters")
    private String label;
    @NotNull(message = "Route cannot be null")
    private String route;
    private String icon;
    private Integer orderNo;
    private Boolean isVisible;
    private Long parentId;
    private List<RoleMenuPermissionDTO> permissions;
}
