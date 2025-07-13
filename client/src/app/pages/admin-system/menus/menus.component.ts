import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';

export interface MenuItem {
    id: number;
    icon: string;
    is_visible: boolean;
    label: string;
    order_no: number;
    route: string;
    parent_id: number | null;
    module: string;
}

@Component({
    selector: 'app-menus',
    imports: [
        SharedModule,
        FormsModule, TableModule, CardModule, DialogModule, DropdownModule, InputTextModule
    ],
    templateUrl: './menus.component.html',
    styleUrl: './menus.component.scss'
})
export class MenusComponent {
    menus: MenuItem[] = [
        {
            id: 1,
            icon: 'pi pi-fw pi-id-card',
            is_visible: true,
            label: 'Bảng điều khiển',
            order_no: 1,
            route: '/dashboard',
            parent_id: null,
            module: 'SYSTEM'
        }
    ];

    selectedMenu: MenuItem | null = null;
    menuDialogVisible = false;
    isEdit = false;

    openAddMenuDialog() {
        this.selectedMenu = {
            id: 0,
            icon: '',
            is_visible: true,
            label: '',
            order_no: 1,
            route: '',
            parent_id: null,
            module: ''
        };
        this.menuDialogVisible = true;
        this.isEdit = false;
    }

    editMenu(menu: MenuItem) {
        this.selectedMenu = { ...menu };
        this.menuDialogVisible = true;
        this.isEdit = true;
    }

    deleteMenu(menu: MenuItem) {
        // show confirm, xong rồi xóa menu
    }
}
