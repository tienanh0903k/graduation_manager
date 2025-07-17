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
import { ProjectTopicService } from '../../../core/services/project-topic.service';
import { TeacherService } from '../../../core/services/teacher.service';

export interface ProjectDTO {
    id: number;
    title: string;
    description: string;
    registrationDate: string;
    status: 'PENDING' | 'APPROVED' | 'REJECTED';
    className: string;
    student: StudentDTO;
}

export interface StudentDTO {
    code: string;
    name: string;
}

@Component({
    selector: 'app-list-project',
    imports: [CommonModule, InputTextModule, TruncatePipe, TagModule, ButtonModule, FormsModule, DialogModule, DropdownModule, ToastModule, PanelModule, TableModule, ConfirmDialogModule],
    templateUrl: './list-project.component.html',
    styleUrl: './list-project.component.scss',
    providers: [MessageService]
})
export class ListProjectComponent implements OnInit {
    projects: ProjectDTO[] = [];
    filteredProjects: ProjectDTO[] = [];
    loading: boolean = false;

    /* ====== tìm kiếm / status ====== */
    searchTitle = '';
    searchStudent = '';
    selectedProject: ProjectDTO | null = null;
    isDetailDialogVisible: boolean = false;
    currentUser: any;

    sortOptions: { label: string; value: boolean | null }[] = [
        { label: 'Tất cả trạng thái', value: null },
        { label: 'Chờ duyệt', value: false },
        { label: 'Đã duyệt', value: true }
    ];

    selectedSort: boolean | null = null;

    constructor(
        private messageService: MessageService,
        private teacherService: TeacherService
    ) {}

    ngOnInit(): void {
        this.loadProjects();
    }

    loadProjects(): void {
        this.loading = true;

        const keyword = this.searchTitle.trim();
        const isApproved = this.selectedSort === null ? true : this.selectedSort;

        this.teacherService.getApprovedProjects(keyword, isApproved).subscribe({
            next: (data) => {
                if (Array.isArray(data)) {
                    this.projects = data.map((item) => ({
                        id: item.projectId,
                        title: item.title,
                        description: item.description,
                        registrationDate: item.registerDate,
                        className: item.className,
                        status: item.approved,
                        student: {
                            code: item.studentCode,
                            name: item.studentName
                        }
                    }));
                    this.filteredProjects = [...this.projects];
                }
                this.loading = false;
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Lỗi', detail: 'Không thể tải đề tài' });
                this.loading = false;
            }
        });
    }

    viewProjectDetails(project: ProjectDTO): void {
        this.selectedProject = project;
        this.isDetailDialogVisible = true;
    }

    approveProject(projectId: number): void {
        this.teacherService.approveProject(projectId).subscribe({
            next: (res) => {
                this.messageService.add({ severity: 'success', summary: 'Thành công', detail: res.message });

                // Update UI status
                const project = this.projects.find((p) => p.id === projectId);
                if (project) project.status = 'APPROVED';
                this.loadProjects();
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Lỗi', detail: 'Không thể phê duyệt đề tài' });
            }
        });
    }

    rejectProject(projectId: number): void {
        // Logic to reject the project
        this.messageService.add({ severity: 'error', summary: 'Error', detail: `Project ${projectId} rejected!` });

        // Update the project status in the array
        const projectIndex = this.projects.findIndex((p) => p.id === projectId);
        if (projectIndex !== -1) {
            this.projects[projectIndex].status = 'REJECTED';
            //this.filterTopics(); // Refresh filtered results
        }
    }

    /* ====== SEARCH + SORT ====== */
    onSearchOrSortChange(): void {
        // Gọi lại API mỗi khi thay đổi tiêu chí tìm kiếm hoặc trạng thái
        this.loadProjects();
    }
}
