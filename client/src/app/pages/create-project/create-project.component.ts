import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';
import { MessageService } from 'primeng/api';
import { ProjectTopicService } from '../../core/services/project-topic.service';
import { DropdownModule } from 'primeng/dropdown';
import { ToastModule } from 'primeng/toast';
import { PanelModule } from 'primeng/panel';
import { TableModule } from 'primeng/table';

@Component({
    selector: 'app-create-project',
    standalone: true,
    imports: [CommonModule, InputTextModule, ButtonModule, FormsModule, DialogModule, DropdownModule, ToastModule, PanelModule, TableModule],
    templateUrl: './create-project.component.html',
    styleUrls: ['./create-project.component.scss'],
    providers: [MessageService]
})
export class CreateProjectComponent {
    isDialogVisible = false;
    topics = [
        {
            title: 'Hệ thống quản lý sinh viên',
            lecturer: 'Nguyễn Văn A',
            department: 'Công nghệ phần mềm',
            description: 'Xây dựng hệ thống quản lý thông tin sinh viên, bao gồm đăng ký môn học, điểm danh, và quản lý điểm số.',
            status: 'Đang chờ duyệt'
        },
        {
            title: 'Ứng dụng AI trong y tế',
            lecturer: 'Trần Thị B',
            department: 'Khoa học dữ liệu',
            description: 'Nghiên cứu và phát triển ứng dụng trí tuệ nhân tạo để hỗ trợ chẩn đoán bệnh trong lĩnh vực y tế.',
            status: 'Đã duyệt'
        }
    ];

    filteredTopics = [...this.topics];

    departments = [
        { label: 'Công nghệ phần mềm', value: 'Công nghệ phần mềm' },
        { label: 'Khoa học dữ liệu', value: 'Khoa học dữ liệu' }
    ];

    lecturers = [
        { label: 'Nguyễn Văn A', value: 1 },
        { label: 'Trần Thị B', value: 2 }
    ];

    constructor(
        private projectTopicService: ProjectTopicService,
        private messageService: MessageService
    ) {}

    form = {
        title: '',
        description: '',
        teacherId: 1
    };

    submitProposal() {
        this.projectTopicService.createTopic(this.form).subscribe({
            next: () => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Đề tài đã được gửi thành công!'
                });
                this.closeProposeDialog();
                this.resetForm();
            },
            error: () => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Lỗi',
                    detail: 'Không thể gửi đề tài. Vui lòng thử lại.'
                });
            }
        });
    }

    openProposeDialog() {
        this.isDialogVisible = true;
    }

    closeProposeDialog() {
        this.isDialogVisible = false;
    }

    resetForm() {
        this.form = {
            title: '',
            description: '',
            teacherId: 1
        };
    }

    selectedDepartment = '';
    selectedLecturer = '';
    searchTitle = '';

    filterTopics() {
        this.filteredTopics = this.topics.filter((topic) => {});
    }
}
