import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { CommonModule, NgIf } from '@angular/common';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';
import { DividerModule } from 'primeng/divider';

@Component({
    selector: 'app-view-council',
    standalone: true,
    imports: [ButtonModule, DividerModule,CommonModule, DropdownModule, FileUploadModule, InputTextModule, PanelModule, FormsModule, DropdownModule,NgIf, CKEditorModule, TableModule],
    templateUrl: './view-council.component.html',
    styleUrl: './view-council.component.scss'
})
export class ViewCouncilComponent {
    referees = [
        {
            role: 'Giáo viên phản biện 1 (Check giữa kỳ)',
            code: '1222',
            name: 'Ngô Thanh Huyền',
            email: 'nthuyenster@gmail.com',
            phone: '0982713518',
            schedule: 'Sáng mai, thứ 6 ngày 6/6 ... P204'
        },
        {
            role: 'Giáo viên phản biện 2',
            code: '1232',
            name: 'Vũ Xuân Thắng',
            email: 'xuanthangutethy@gmail.com',
            phone: '0988169829',
            schedule: 'Thứ 5 ngày 5-6-2025'
        }
    ];

    council = {
        name: 'Hội đồng 2 - 206',
        date: new Date('2025-06-07'),
        members: [
            { code: '1248', name: 'Nguyễn Minh Tiến', department: 'KHMT', role: 'Chủ tịch hội đồng' },
            { code: '1242', name: 'Trần Đỗ Thu Hà', department: 'CNPM', role: 'Thư ký' },
            { code: '1236', name: 'Hoàng Quốc Việt', department: 'CNPM', role: 'Ủy viên' },
            { code: '1217', name: 'Vũ Khánh Quý', department: 'HTTT', role: 'Ủy viên' },
            { code: '1234', name: 'Đặng Văn Anh', department: 'KHMT', role: 'Ủy viên' }
        ]
    };

    roles = [
        { label: 'Chủ tịch hội đồng', value: 'Chủ tịch hội đồng' },
        { label: 'Thư ký', value: 'Thư ký' },
        { label: 'Ủy viên', value: 'Ủy viên' }
    ];

    isTodayOrPast(date: Date): boolean {
        const today = new Date();
        return new Date(date) <= today;
    }
}
