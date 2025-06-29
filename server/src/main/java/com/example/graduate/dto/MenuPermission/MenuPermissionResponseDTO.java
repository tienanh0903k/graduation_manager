package com.example.graduate.dto.MenuPermission;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MenuPermissionResponseDTO {
    private Long id;
    private String label;
    private String route;
    private String icon;
    private Integer orderNo;
    private Boolean isVisible;
    private String module;
    private Long parentId;
    private List<PermissionDTO> permissions;
}
