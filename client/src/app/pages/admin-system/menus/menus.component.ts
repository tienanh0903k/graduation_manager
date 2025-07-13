import { Component, OnInit } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { MenuItemsService } from '../../../core/services/menu.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';

export interface IMenuItem {
    id: number;
    icon: string;
    isVisible: boolean;
    label: string;
    orderNo: number;
    route: string;
    parentId: number | null;
}

@Component({
    selector: 'app-menus',
    standalone: true,
    imports: [SharedModule, FormsModule, TableModule, ToastModule,CardModule, DialogModule, DropdownModule, InputTextModule, DialogModule, ConfirmDialogModule],
    templateUrl: './menus.component.html',
    styleUrl: './menus.component.scss',
    providers: [MessageService, ConfirmationService],
})
export class MenusComponent implements OnInit {
    menus: IMenuItem[] = [];

    selectedMenu: IMenuItem | null = null;

    menuDialogVisible = false;
    isEdit = false;

    constructor(private menuItemsService: MenuItemsService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService
    ) {}

    ngOnInit(): void {
        this.loadMenus();
    }

    loadMenus() {
        this.menuItemsService.getAll().subscribe((data) => {
            this.menus = data;
        });
    }



    saveMenu() {
        if (!this.selectedMenu) return;
        if (this.isEdit) {
            this.menuItemsService.update(this.selectedMenu.id, this.selectedMenu).subscribe(() => {
                this.loadMenus();
                this.menuDialogVisible = false;
            });
        } else {
            this.menuItemsService.create(this.selectedMenu).subscribe(() => {
                this.messageService.add({ severity: 'success', summary: 'Thành công', detail: 'Thêm menu thành công' });
                this.loadMenus();
                this.menuDialogVisible = false;
            });
        }
    }


    openAddMenuDialog() {
        this.selectedMenu = {
            id: 0,
            icon: '',
            isVisible: true,
            label: '',
            orderNo: 1,
            route: '',
            parentId: null
        };
        this.menuDialogVisible = true;
        this.isEdit = false;
    }

    editMenu(menu: IMenuItem) {
        this.selectedMenu = { ...menu };
        this.menuDialogVisible = true;
        this.isEdit = true;
    }

    deleteMenu(menu: IMenuItem) {
        this.confirmationService.confirm({
        message: `Bạn có chắc chắn muốn xoá menu "${menu.label}"?`,
        header: 'Xác nhận xoá',
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: 'Xoá',
        rejectLabel: 'Huỷ',
        acceptButtonStyleClass: 'p-button-danger',
        accept: () => {
            this.menuItemsService.delete(menu.id).subscribe(() => {
                this.loadMenus();
            });
        }
    });
    }
}
