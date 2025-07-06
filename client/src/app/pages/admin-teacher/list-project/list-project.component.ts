import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';
import { MessageService } from 'primeng/api';
import { DropdownModule } from 'primeng/dropdown';
import { ToastModule } from 'primeng/toast';
import { PanelModule } from 'primeng/panel';
import { TableModule } from 'primeng/table';
import { ConfirmationService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TagModule } from 'primeng/tag';
import { TruncatePipe } from '../../../shared/pipes/truncate.pipe';

@Component({
    selector: 'app-list-project',
    imports: [CommonModule, InputTextModule, TruncatePipe ,TagModule ,ButtonModule, FormsModule, DialogModule, DropdownModule, ToastModule, PanelModule, TableModule, ConfirmDialogModule],
    templateUrl: './list-project.component.html',
    styleUrl: './list-project.component.scss',
    providers: [MessageService]
})
export class ListProjectComponent implements OnInit {
    projects: any[] = [];
    loading: boolean = false;
    selectedProject: any;
    isDetailDialogVisible: boolean = false;
    currentUser: any;

    constructor(private messageService: MessageService) {}

    ngOnInit(): void {}

    loadProjects(): void {
        this.loading = true;
        // Simulate an API call to fetch projects
        setTimeout(() => {
            this.projects = [
                { id: 1, name: 'Project A', status: 'Pending' },
                { id: 2, name: 'Project B', status: 'Approved' },
                { id: 3, name: 'Project C', status: 'Rejected' }
            ];
            this.loading = false;
        }, 1000);
    }

    viewProjectDetails(projectId: number): void {
        this.selectedProject = projectId;
        this.isDetailDialogVisible = true;
    }

    approveProject(projectId: number): void {
        // Logic to approve the project
        this.messageService.add({ severity: 'success', summary: 'Success', detail: `Project ${projectId} approved successfully!` });
    }

    rejectProject(projectId: number): void {
        // Logic to reject the project
        this.messageService.add({ severity: 'error', summary: 'Error', detail: `Project ${projectId} rejected!` });
    }

getStatusText(status: string): string {
  switch (status) {
    case 'PENDING':
      return 'Chờ duyệt';
    case 'APPROVED':
      return 'Đã duyệt';
    case 'REJECTED':
      return 'Đã từ chối';
    default:
      return status;
  }
}

getStatusSeverity(status: string): string {
  switch (status) {
    case 'PENDING':
      return 'warning';
    case 'APPROVED':
      return 'success';
    case 'REJECTED':
      return 'danger';
    default:
      return 'info';
  }
}
}
