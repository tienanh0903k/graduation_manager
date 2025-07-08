import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ProjectTopicService } from '../../core/services/project-topic.service';
import { SharedModule } from '../../shared/shared.module';
import { TextareaModule } from 'primeng/textarea';

@Component({
    selector: 'app-create-project',
    standalone: true,
    imports: [SharedModule, TextareaModule ],
    templateUrl: './create-project.component.html',
    styleUrls: ['./create-project.component.scss'],
    providers: [MessageService]
})
export class CreateProjectComponent implements OnInit {
    isDialogVisible = false;

    topics: any[] = [];
    filteredTopics: any[] = [];

    departments = [
        { label: '125213', value: 125213 },
        { label: '125214', value: 125214 }
    ];

    teacherOptions = [
        { label: 'Nguyễn Văn A', value: 1 },
        { label: 'Trần Thị B', value: 2 }
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
        private messageService: MessageService
    ) {}

    ngOnInit(): void {
        this._loadTopics();
    }

    private _loadTopics(): void {
        this.projectTopicService.getAll().subscribe({
            next: (data) => {
                this.topics = data;
                this.filteredTopics = [...this.topics];
            },
            error: () => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Lỗi',
                    detail: 'Không thể tải danh sách đề tài. Vui lòng thử lại.'
                });
            }
        });
    }

    /**
     * Gửi đề xuất đề tài mới
     */
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
                this._loadTopics(); // Tải lại danh sách
            },
            error: (err) => {
                console.error('Error creating topic:', err)
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

    /**
     * Lọc danh sách đề tài theo bộ lọc
     */
    filterTopics(): void {
        this.filteredTopics = this.topics.filter((topic) => {
            const matchesDepartment = !this.selectedDepartment || topic.department === this.selectedDepartment;
            const matchesLecturer =
                !this.selectedLecturer ||
                topic.lecturer.toLowerCase().includes(this.getLecturerNameById(this.selectedLecturer).toLowerCase());
            const matchesTitle =
                !this.searchTitle || topic.title.toLowerCase().includes(this.searchTitle.toLowerCase());

            return matchesDepartment && matchesLecturer && matchesTitle;
        });
    }

    private getLecturerNameById(id: number | string): string {
        const lecturer = this.teacherOptions.find((t) => t.value === +id);
        return lecturer?.label || '';
    }
}
