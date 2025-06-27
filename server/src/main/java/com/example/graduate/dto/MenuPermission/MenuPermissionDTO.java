package com.example.graduate.dto.MenuPermission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuPermissionDTO {
    private Long menuId; 
    private boolean canRead;
    private boolean canCreate;
    private boolean canUpdate;
    private boolean canDelete;
}
