import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ProjectTopicService } from '../../core/services/project-topic.service';
import { SharedModule } from '../../shared/shared.module';
import { TextareaModule } from 'primeng/textarea';
import { StudentsService } from '../../core/services/students.service';

@Component({
    selector: 'app-create-project',
    standalone: true,
    imports: [SharedModule, TextareaModule],
    templateUrl: './create-project.component.html',
    styleUrls: ['./create-project.component.scss'],
    providers: [MessageService]
})
export class CreateProjectComponent implements OnInit {
    isDialogVisible = false;

    topics: any[] = [];
    totalRecords = 0;
    currentPage = 0;
    rowsPerPage = 10;

    departments = [
        { label: '125213', value: '125213' },
        { label: '125214', value: '125214' }
    ];

    teacherOptions = [
        { label: 'Nguyễn Văn A', value: 'Nguyễn Văn A' },
        { label: 'Trần Thị B', value: 'Trần Thị B' }
    ];

    selectedDepartment = '';
    selectedLecturer = '';
    searchTitle = '';

    form = {
        title: '',
        description: '',
        teacherId: 1
    };

    constructor(
        private projectTopicService: ProjectTopicService,
        private messageService: MessageService,
        private studentsService: StudentsService
    ) {}

    ngOnInit(): void {
        this.loadSearchResults();
    }

    loadSearchResults(): void {
        this.studentsService.searchStudentProjects(
            this.selectedDepartment || undefined,
            this.selectedLecturer || undefined,
            this.searchTitle || undefined,
            this.currentPage,
            this.rowsPerPage
        ).subscribe({
            next: (res) => {
                this.topics = res.content; // nếu backend trả kiểu Page
                this.totalRecords = res.totalElements;
            },
            error: () =>
                this.messageService.add({
                    severity: 'error',
                    summary: 'Lỗi',
                    detail: 'Không thể tìm kiếm đề tài. Vui lòng thử lại.'
                })
        });
    }

    onPageChange(event: any): void {
        this.currentPage = event.first / event.rows;
        this.rowsPerPage = event.rows;
        this.loadSearchResults();
    }

    submitProposal(): void {
        this.projectTopicService.createTopic(this.form).subscribe({
            next: () => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Đề tài đã được gửi thành công!'
                });
                this.closeProposeDialog();
                this.resetForm();

                // Reset bộ lọc nếu muốn
                this.selectedDepartment = '';
                this.selectedLecturer = '';
                this.searchTitle = '';

                this.currentPage = 0; // về trang đầu
                this.loadSearchResults();
            },
            error: (err) => {
                console.error('Error creating topic:', err);
                this.messageService.add({
                    severity: 'error',
                    summary: 'Lỗi',
                    detail: 'Không thể gửi đề tài. Vui lòng thử lại.'
                });
            }
        });
    }

    openProposeDialog(): void {
        this.isDialogVisible = true;
    }

    closeProposeDialog(): void {
        this.isDialogVisible = false;
    }

    resetForm(): void {
        this.form = {
            title: '',
            description: '',
            teacherId: 1
        };
    }

    filterTopics(): void {
        this.currentPage = 0;
        this.loadSearchResults();
    }
}
