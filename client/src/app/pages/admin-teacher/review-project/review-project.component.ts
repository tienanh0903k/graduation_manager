import { Component, OnInit } from '@angular/core';
import { ManageProjectComponent } from '../../manage-project/manage-project.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';

@Component({
    selector: 'app-review-project',
    imports: [ManageProjectComponent,
          CommonModule,
    FormsModule,
    DropdownModule,
    ],
    templateUrl: './review-project.component.html',
    styleUrl: './review-project.component.scss'
})
export class ReviewProjectComponent implements OnInit {
    students = [
        { label: 'Nguyễn Văn A', value: 1 },
        { label: 'Trần Thị B', value: 2 },
        { label: 'Lê Quang C', value: 3 }
    ];

    selectedStudent: any;
    selectedStudentReports: any[] = [];
    ngOnInit(): void {}
    onStudentChanged(student: any) {
        console.log('Sinh viên được chọn:', student);
        this.selectedStudentReports = [
            { week: 'Tuần 1', content: 'Báo cáo tuần 1', reportLink: 'link1', comment: 'Tốt', score: '8' },
            { week: 'Tuần 2', content: 'Báo cáo tuần 2', reportLink: 'link2', comment: 'Cần cải thiện', score: '6' }
        ];
    }
}
