import { Component, OnInit } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { MessageService } from 'primeng/api';
import { TextareaModule } from 'primeng/textarea';
import { PickListModule } from 'primeng/picklist';
import { RoleService } from '../../../core/services/role.service';

@Component({
    selector: 'app-role',
    imports: [SharedModule, TextareaModule, PickListModule],
    templateUrl: './role.component.html',
    styleUrl: './role.component.scss',
    standalone: true,
    providers: [MessageService]
})
export class RoleComponent implements OnInit {
    constructor(
        private messageService: MessageService,
        private roleService: RoleService
    ) {}

    ngOnInit(): void {}

    roles = [
        { id: 1, name: 'ADMIN' },
        { id: 2, name: 'TEACHER' },
        { id: 3, name: 'STUDENT' }
    ];

    menus = [
        { label: 'Dashboard', value: 'dashboard' },
        { label: 'Quản lý sinh viên', value: 'student' },
        { label: 'Quản trị hệ thống', value: 'admin' },
        { label: 'Thống kê đề tài', value: 'stats' },
        { label: 'Cài đặt', value: 'settings' }
    ];

    assignedMenus: any[] = [];
    unassignedMenus: any[] = [];

    isRoleDialogVisible = false;
    selectedRole: any = null;

    openRoleMenuDialog(role: any): void {
        this.selectedRole = role;
        this.isRoleDialogVisible = true;

        this.roleService.getMenusByRole(role.name).subscribe({
            next: (res) => {
                this.assignedMenus = res.assigned;
                this.unassignedMenus = res.unassigned;
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Lỗi', detail: 'Không thể tải menu cho role' });
                this.isRoleDialogVisible = false;
            }
        });
    }

    saveRoleMenus(): void {
        const menuIds = this.assignedMenus.map((m) => m.id); // backend cần mảng id
        this.roleService.updateMenusForRole(this.selectedRole.name, menuIds).subscribe({
            next: () => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: `Đã lưu menu cho role ${this.selectedRole.name}`
                });
                this.isRoleDialogVisible = false;
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Lỗi', detail: 'Không thể lưu menu' });
            }
        });
    }
}
