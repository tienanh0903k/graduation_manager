package com.example.graduate.dto.MenuPermission;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MenuPermissionResponseDTO {
    private String label;
    private String route;
    private String icon;
    private List<PermissionDTO> permissions;
}
