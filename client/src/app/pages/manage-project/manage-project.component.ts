import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { NgIf } from '@angular/common';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { TableModule } from 'primeng/table';
@Component({
    selector: 'app-manage-project',
    standalone: true,
    imports: [FormsModule, ReactiveFormsModule, InputTextModule, ButtonModule, TableModule, CKEditorModule, DropdownModule, FileUploadModule, PanelModule, NgIf],
    templateUrl: './manage-project.component.html',
    styleUrl: './manage-project.component.scss',
    encapsulation: ViewEncapsulation.None
})
export class ManageProjectComponent implements OnInit {
    @Input() students: any[] = [];
    @Input() selectedStudent: any;
    @Input() selectedStudentReports: any[] = [];

    public Editor = ClassicEditor;

    public task = {
        reportDetail: ''
    };

    public config = {
        licenseKey: 'GPL', // ✅ Thêm dòng này để tránh lỗi

        placeholder: 'Nhập nội dung báo cáo...',
        toolbar: ['heading', '|', 'bold', 'italic', 'link', '|', 'undo', 'redo']
    };

    public isEditorReady = true;

    project = {
        lecturer: 'Nguyễn Văn Quyết',
        title: 'Xây dựng website tìm kiếm việc làm Công nghệ Thông tin'
    };

    public model = {
        name: 'Hardik',
        description: '<p>This is a sample form using CKEditor.</p>'
    };

    ngOnInit(): void {
        console.log();
    }

    weeks = Array.from({ length: 20 }, (_, i) => ({ label: `Tuần ${i + 1}`, value: i + 1 }));
    selectedWeek = 17;
    startDate!: Date;
    endDate!: Date;

    rows = 10;
    currentPage = 0;

    myProjectReports = Array.from({ length: 17 }, (_, i) => ({
        week: `Tuần ${17 - i}`, // giảm dần từ Tuần 17 -> Tuần 1
        content: '',
        reportLink: '',
        comment: '',
        score: ''
    }));

    editReport(row: any) {
        console.log('Editing', row);
        // mở dialog chỉnh sửa nội dung báo cáo tuần
    }
}
