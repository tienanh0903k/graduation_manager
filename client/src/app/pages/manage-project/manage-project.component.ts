import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { CommonModule, NgIf } from '@angular/common';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { TableModule } from 'primeng/table';
import { WeeklyReportService } from '../../core/services/weekly-report.service';
import { TruncatePipe } from '../../shared/pipes/truncate.pipe';

@Component({
    selector: 'app-manage-project',
    standalone: true,
    imports: [FormsModule, ReactiveFormsModule, InputTextModule, ButtonModule, TableModule, CKEditorModule, DropdownModule, FileUploadModule, PanelModule, NgIf, CommonModule],
    templateUrl: './manage-project.component.html',
    styleUrl: './manage-project.component.scss',
    encapsulation: ViewEncapsulation.None,
    providers: [TruncatePipe]
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
        licenseKey: 'GPL',
        placeholder: 'Nhập nội dung báo cáo...',
        toolbar: ['heading', '|', 'bold', 'italic', 'link', '|', 'undo', 'redo']
    };

    public isEditorReady = true;

    project = {
        lecturer: 'Nguyễn Văn Quyết', // Default value, sẽ được override bởi API
        title: 'Xây dựng website tìm kiếm việc làm Công nghệ Thông tin' // Default value, sẽ được override bởi API
    };

    public model = {
        name: 'Hardik',
        description: '<p>This is a sample form using CKEditor.</p>'
    };

    // Thêm các property để lưu dữ liệu edit
    currentWork = '';
    currentContent = '';
    currentResult = '';
    currentEditingReport: any = null;
    isEditMode = false;

    constructor(private weeklyReportService: WeeklyReportService) {}

    ngOnInit(): void {
        const today = new Date();
        const todayStr = today.toISOString().split('T')[0];
        this.weeklyReportService.getReportsByDate(todayStr).subscribe({
            next: (data) => {
                this.myProjectReports = data.map((item: any) => ({
                    week: `Tuần ${item.weekNumber}`,
                    content: item.task,
                    reportLink: item.fileLink,
                    comment: item.comment,
                    score: item.score,
                    weekNumber: item.weekNumber,
                    startDate: item.startDate,
                    endDate: item.endDate,
                    guide: item.guide,
                    studentProjectId: item.studentProjectId,
                    // Thêm các field khác từ API response
                    rawData: item
                }));

                // Fill thông tin từ API response
                if (data.length > 0) {
                    // Fill thông tin giảng viên và đề tài từ API (override default values)
                    this.project.lecturer = data[0].teacherName || this.project.lecturer;
                    this.project.title = data[0].projectTitle || this.project.title;

                    // Fill thông tin sinh viên
                    this.selectedStudent = {
                        name: data[0].studentName,
                        code: data[0].studentCode,
                        className: data[0].className
                    };
                }
            },
            error: () => {
                console.error('Không thể tải báo cáo tuần');
            }
        });
    }

    weeks = Array.from({ length: 20 }, (_, i) => ({ label: `Tuần ${i + 1}`, value: i + 1 }));
    selectedWeek = 17;
    startDate!: Date;
    endDate!: Date;
    rows = 10;
    currentPage = 0;

    myProjectReports = Array.from({ length: 17 }, (_, i) => ({
        week: `Tuần ${17 - i}`,
        content: '',
        reportLink: '',
        comment: '',
        score: ''
    }));

    editReport(row: any) {
        console.log('Editing', row);

        // Chuyển sang chế độ edit
        this.isEditMode = true;
        this.currentEditingReport = row;

        // Fill dữ liệu vào form
        this.selectedWeek = row.weekNumber || parseInt(row.week.replace('Tuần ', ''));

        // Tính toán ngày bắt đầu và kết thúc
        if (row.startDate) {
            this.startDate = new Date(row.startDate);
        }
        if (row.endDate) {
            this.endDate = new Date(row.endDate);
        }

        // Fill các field trong form
        this.currentWork = row.content || row.rawData?.task || '';
        this.currentContent = row.guide || row.rawData?.guide || '';
        this.currentResult = row.comment || ''; // hoặc field khác tùy theo logic

        // Fill CKEditor content
        this.task.reportDetail = row.rawData?.content || '';

        // Scroll đến form edit (optional)
        const formElement = document.querySelector('.form-grid');
        if (formElement) {
            formElement.scrollIntoView({ behavior: 'smooth' });
        }
    }

    // Thêm method để save changes
    saveReport() {
        if (!this.currentEditingReport) return;

        const updatedData = {
            weekNumber: this.selectedWeek,
            task: this.currentWork,
            guide: this.currentContent,
            content: this.task.reportDetail,
            startDate: this.startDate?.toISOString().split('T')[0],
            endDate: this.endDate?.toISOString().split('T')[0],
            studentProjectId: this.currentEditingReport.studentProjectId
        };

        // Call API để update
        // this.weeklyReportService.updateReport(updatedData).subscribe({
        //     next: (response) => {
        //         console.log('Report updated successfully', response);

        //         // Update local data
        //         const index = this.myProjectReports.findIndex(r => r.weekNumber === this.selectedWeek);
        //         if (index !== -1) {
        //             this.myProjectReports[index] = {
        //                 ...this.myProjectReports[index],
        //                 content: this.currentWork,
        //                 guide: this.currentContent,
        //                 // Update other fields as needed
        //             };
        //         }

        //         this.cancelEdit();
        //     },
        //     error: (error) => {
        //         console.error('Error updating report', error);
        //     }
        // });
    }

    // Method để hủy edit
    cancelEdit() {
        this.isEditMode = false;
        this.currentEditingReport = null;
        this.currentWork = '';
        this.currentContent = '';
        this.currentResult = '';
        this.task.reportDetail = '';
    }
}
