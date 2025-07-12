import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { FileUploadModule } from 'primeng/fileupload';
import { TableModule } from 'primeng/table';
import * as XLSX from 'xlsx';
import { SharedModule } from '../../../shared/shared.module';
import { CardModule } from 'primeng/card';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { ImportExcelDialogComponent } from '../components/import-excel-dialog/import-excel-dialog.component';
import { MessageService } from 'primeng/api';
import { StudentsService } from '../../../core/services/students.service';

@Component({
    selector: 'app-students',
    imports: [SharedModule, FileUploadModule, FormsModule, TableModule, CardModule, DialogModule, DropdownModule, InputTextModule, ImportExcelDialogComponent],
    templateUrl: './students.component.html',
    styleUrls: ['./students.component.scss'],
    standalone: true,
    providers: [MessageService]
})
export class StudentsComponent {
    importDialogVisible = false;
    importPreview: any[] = [];
    addStudentDialogVisible = false;
    newStudent = { name: '', mssv: '', email: '', class_name: '', password: '' };

    genderOptions = [
        { label: 'Nam', value: 'Nam' },
        { label: 'Nữ', value: 'Nữ' }
    ];

    classOptions = [
        { label: 'CNTT K65A', value: '125213' },
        { label: 'CNTT K65B', value: '125214' },
        { label: 'CNTT K66A', value: '125215' }
    ];

    students: any[] = [];
    filterName = '';
    filterMSSV = '';
    filterClass = '';

    get filteredStudents() {
        return this.students.filter(
            (s) => (!this.filterName || s.name?.toLowerCase().includes(this.filterName.toLowerCase())) && (!this.filterMSSV || s.mssv?.toLowerCase().includes(this.filterMSSV.toLowerCase())) && (!this.filterClass || s.class_name === this.filterClass)
        );
    }

    constructor(
        private messageService: MessageService,
        private studentsService: StudentsService
    ) {}

    // Hiển thị dialog thêm sinh viên
    showAddStudentDialog() {
        this.newStudent = { name: '', mssv: '', email: '', class_name: '', password: '' }; // Reset dữ liệu
        this.addStudentDialogVisible = true;
    }

    // Lưu sinh viên mới
    saveNewStudent() {
        if (this.newStudent.name && this.newStudent.mssv && this.newStudent.email && this.newStudent.class_name && this.newStudent.password) {
            this.students.push({ ...this.newStudent });
            this.closeAddStudentDialog();
        } else {
            alert('Vui lòng điền đầy đủ thông tin!');
        }
    }

    // Đóng dialog thêm sinh viên
    closeAddStudentDialog() {
        this.addStudentDialogVisible = false;
    }

    // Xử lý khi dialog đóng
    onAddDialogHide() {
        this.newStudent = { name: '', mssv: '', email: '', class_name: '', password: '' }; // Reset khi đóng
    }

    addRow() {
        this.students.push({
            mssv: '',
            name: '',
            email: '',
            class_name: '',
            gender: ''
        });
    }

    openImportDialog() {
        console.log('Opening import dialog...');
        this.importDialogVisible = true;
    }

    handleImportedStudents(students: any[]) {
        console.log('Imported students:', students);
        this.studentsService.importStudents(students).subscribe({
            next: (response) => {
                //using toast
                this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Imported successfully' });
                this.importDialogVisible = false;
            },
            error: (error) => {
                console.error('Error importing students', error);
                alert('Có lỗi khi nhập danh sách sinh viên!');
            }
        });
        this.importDialogVisible = false;
    }

    onDialogVisibleChange(visible: boolean) {
        this.importDialogVisible = visible;
    }
}
