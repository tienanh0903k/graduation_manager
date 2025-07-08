import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TagModule } from 'primeng/tag';
import { InputNumberModule } from 'primeng/inputnumber';
import { TruncatePipe } from '../../../shared/pipes/truncate.pipe';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { PanelModule } from 'primeng/panel';
import { SharedModule } from '../../../shared/shared.module';

@Component({
    selector: 'app-student-review',
    standalone: true,
    imports: [SharedModule],
    providers: [MessageService],
    templateUrl: './student-review.component.html',
    styleUrls: ['./student-review.component.scss']
})
export class StudentReviewComponent {
    loading: boolean = false;
    selectedSort: string | null = null;
    sortOptions = [
        { label: 'Tên A-Z', value: 'name_asc' },
        { label: 'Tên Z-A', value: 'name_desc' },
        { label: 'Ngày đăng ký mới nhất', value: 'date_desc' },
        { label: 'Ngày đăng ký cũ nhất', value: 'date_asc' }
    ];

    studentProjects: any[] = [];
    selectedItem: any;
    isEditDialogVisible = false;
    isDetailDialogVisible = false;
    gradeValue: number = 0;
    gradeNote: string = '';

    searchStudent: string = '';
    searchTitle: string = '';

    constructor(private toast: MessageService) {}

    addGrade(item: any) {
        this.selectedItem = item;
        this.gradeValue = item.grade || 0;
        this.gradeNote = item.note || '';
        this.isEditDialogVisible = true;
    }

    viewStudentDetails(item: any) {
        this.selectedItem = item;
        this.isDetailDialogVisible = true;
    }

    openEditDialog(item: any) {
        this.selectedItem = item;
        this.gradeValue = item.grade || 0;
        this.gradeNote = item.note || '';
        this.isEditDialogVisible = true;
    }

    getStatusSeverity(status: string): string {
        switch (status) {
            case 'APPROVED': return 'success';
            case 'PENDING': return 'warning';
            case 'REJECTED': return 'danger';
            default: return '';
        }
    }

    getStatusText(status: string): string {
        switch (status) {
            case 'APPROVED': return 'Đã duyệt';
            case 'PENDING': return 'Chờ duyệt';
            case 'REJECTED': return 'Bị từ chối';
            default: return 'Không rõ';
        }
    }

    submitGrade() {
        if (this.selectedItem) {
            this.selectedItem.grade = this.gradeValue;
            this.selectedItem.note = this.gradeNote;

            this.isEditDialogVisible = false;
            this.toast.add({
                severity: 'success',
                summary: 'Thành công',
                detail: 'Đã cập nhật điểm và ghi chú'
            });
        }
    }

    filterStudents() {
        // TODO: Thêm logic filter dựa vào searchStudent, searchTitle, selectedSort
    }
}
