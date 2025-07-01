import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { TagModule } from 'primeng/tag';
import { BreadcrumbUtil } from '../../core/utils/breadcrumb.util';
import { Router } from '@angular/router';
import { PanelModule } from 'primeng/panel';
import { DialogModule } from 'primeng/dialog';

@Component({
    selector: 'app-create-project',
    standalone: true,
    imports: [CommonModule, TableModule, DropdownModule, InputTextModule, ButtonModule, FormsModule, TagModule, PanelModule, DialogModule],
    templateUrl: './create-project.component.html',
    styleUrls: ['./create-project.component.scss']
})
export class CreateProjectComponent {
    topics = [
        {
            title: 'Xây dựng website tìm kiếm việc làm Công nghệ Thông tin',
            lecturer: 'Nguyễn Văn Quyết',
            email: 'quyetict@hust.edu.vn',
            phone: '0912188636',
            description: 'Hệ thống hỗ trợ sinh viên tìm việc làm ngành CNTT.',
            studentCount: '15/15',
            department: 'Hệ thống thông tin',
            status: 'Đã được duyệt'
        },
        {
            title: 'Phát triển ứng dụng quản lý lớp học trực tuyến',
            lecturer: 'Trần Thị Mai',
            email: 'maitt@hust.edu.vn',
            phone: '0905123456',
            description: 'Ứng dụng giúp GV quản lý bài tập, điểm số, sinh viên.',
            studentCount: '10/15',
            department: 'Khoa học máy tính',
            status: 'Chờ duyệt'
        },
        {
            title: 'AI hỗ trợ chẩn đoán hình ảnh y tế',
            lecturer: 'Lê Văn Nam',
            email: 'namlv@hust.edu.vn',
            phone: '0987654321',
            description: 'Dự án áp dụng machine learning vào xử lý ảnh y tế.',
            studentCount: '12/15',
            department: 'Khoa học máy tính',
            status: 'Đã được duyệt'
        }
    ];

    form = {
        title: '',
        lecturer: '',
        email: '',
        phone: '',
        description: '',
        studentCount: '',
        department: '',
        status: 'Chờ duyệt'
    };

    submitProposal() {
        this.topics.push({ ...this.form });
        this.filteredTopics = [...this.topics]; // cập nhật lại bảng
        this.closeProposeDialog();

        // Reset form sau khi lưu
        this.form = {
            title: '',
            lecturer: '',
            email: '',
            phone: '',
            description: '',
            studentCount: '',
            department: '',
            status: 'Chờ duyệt'
        };
    }
    breadcrumbLabel = '';
    isDialogVisible: boolean = false;

    constructor(private router: Router) {
        this.breadcrumbLabel = BreadcrumbUtil.getLabelFromPath(this.router.url);
    }

    filteredTopics = [...this.topics];

    selectedDepartment = '';
    searchTitle = '';
    selectedLecturer = '';

    departments = [
        { label: 'Hệ thống thông tin', value: 'Hệ thống thông tin' },
        { label: 'Khoa học máy tính', value: 'Khoa học máy tính' }
    ];

    lecturers = [{ label: 'Nguyễn Văn Quyết', value: 'Nguyễn Văn Quyết' }];

    filterTopics() {
        this.filteredTopics = this.topics.filter((t) => {
            const matchDept = this.selectedDepartment ? t.department === this.selectedDepartment : true;
            const matchLecturer = this.selectedLecturer ? t.lecturer === this.selectedLecturer : true;
            const matchTitle = this.searchTitle ? t.title.toLowerCase().includes(this.searchTitle.toLowerCase()) : true;
            return matchDept && matchLecturer && matchTitle;
        });
    }

    openProposeDialog() {
        this.isDialogVisible = true;
    }

    closeProposeDialog() {
        this.isDialogVisible = false;
    }
}
