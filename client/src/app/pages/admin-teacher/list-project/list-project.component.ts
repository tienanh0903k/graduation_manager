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
    imports: [CommonModule, InputTextModule, TruncatePipe, TagModule, ButtonModule, FormsModule, DialogModule, DropdownModule, ToastModule, PanelModule, TableModule, ConfirmDialogModule],
    templateUrl: './list-project.component.html',
    styleUrl: './list-project.component.scss',
    providers: [MessageService]
})
export class ListProjectComponent implements OnInit {
    projects: any[] = [];
    loading: boolean = false;

    /* ====== tìm kiếm / sort ====== */
    searchTitle = '';
    searchStudent = '';
    selectedProject: any;
    isDetailDialogVisible: boolean = false;
    currentUser: any;
    sortOptions = [
        { label: 'Ngày đăng ký mới nhất', value: 'DATE_DESC' },
        { label: 'Ngày đăng ký cũ nhất', value: 'DATE_ASC' },
        { label: 'Tên A → Z', value: 'TITLE_ASC' },
        { label: 'Tên Z → A', value: 'TITLE_DESC' }
    ];
    selectedSort: string | null = null;

    constructor(private messageService: MessageService) {}

    ngOnInit(): void {
        this.loadProjects();
    }

    loadProjects(): void {
        this.loading = true;

        /* Giả lập API 1s */
        setTimeout(() => {
            this.projects = mockProjects; // mock bên dưới
            //   this.filteredProjects = [...this.projects];
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
    /* ====== SEARCH + SORT ====== */
    filterTopics(): void {
        let data = [...this.projects];

        /* -- Lọc theo tiêu đề -- */
        if (this.searchTitle.trim()) {
            const kw = this.searchTitle.toLowerCase();
            data = data.filter((p) => p.title.toLowerCase().includes(kw));
        }

        /* -- Lọc theo sinh viên -- */
        if (this.searchStudent.trim()) {
            const kw = this.searchStudent.toLowerCase();
            data = data.filter((p) => p.students.some((s: any) => s.name.toLowerCase().includes(kw) || s.code.toLowerCase().includes(kw)));
        }

        /* -- Sort -- */
        switch (this.selectedSort) {
            case 'DATE_DESC':
                data.sort((a, b) => +new Date(b.registrationDate) - +new Date(a.registrationDate));
                break;
            case 'DATE_ASC':
                data.sort((a, b) => +new Date(a.registrationDate) - +new Date(b.registrationDate));
                break;
            case 'TITLE_ASC':
                data.sort((a, b) => a.title.localeCompare(b.title));
                break;
            case 'TITLE_DESC':
                data.sort((a, b) => b.title.localeCompare(a.title));
                break;
        }
    }
}
interface ProjectDTO {
  id: number;
  title: string;
  description: string;
  objectives: string;
  requirements: string;
  registrationDate: string;      // ISO string
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  students: StudentDTO[];
}

interface StudentDTO {
  id: number;
  code: string;
  name: string;
}
const mockProjects: ProjectDTO[] = [
  {
    id: 1,
    title: 'Xây dựng hệ thống quản lý thư viện',
    description: 'Ứng dụng giúp quản lý sách, độc giả và mượn trả.',
    objectives: '…',
    requirements: '…',
    registrationDate: '2025-07-01',
    status: 'PENDING',
    students: [{ id: 101, code: 'SV2020001', name: 'Nguyễn Văn A' }]
  },
  {
    id: 2,
    title: 'Website bán hàng thời trang',
    description: 'Trang web thương mại điện tử chuyên về quần áo.',
    objectives: '…',
    requirements: '…',
    registrationDate: '2025-07-02',
    status: 'APPROVED',
    students: [{ id: 102, code: 'SV2020002', name: 'Trần Thị B' }]
  },
  {
    id: 3,
    title: 'Phân tích dữ liệu du lịch Việt Nam',
    description: 'Dashboard trực quan hóa xu hướng du lịch.',
    objectives: '…',
    requirements: '…',
    registrationDate: '2025-06-28',
    status: 'REJECTED',
    students: [
      { id: 103, code: 'SV2020003', name: 'Phạm Công C' },
      { id: 104, code: 'SV2020004', name: 'Lê Thị D' }
    ]
  }
];
