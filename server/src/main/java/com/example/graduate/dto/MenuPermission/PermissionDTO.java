package com.example.graduate.dto.MenuPermission;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDTO {
    private boolean canRead;
    private boolean canCreate;
    private boolean canUpdate;
    private boolean canDelete;
}
