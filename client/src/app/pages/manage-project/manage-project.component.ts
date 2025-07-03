import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { loadCKEditorCloud, CKEditorCloudResult } from '@ckeditor/ckeditor5-angular';
import type { ClassicEditor, EditorConfig } from 'https://cdn.ckeditor.com/typings/ckeditor5.d.ts';

@Component({
    selector: 'app-manage-project',
    standalone: true,
    imports: [FormsModule, ReactiveFormsModule, InputTextModule, ButtonModule, DropdownModule, FileUploadModule, PanelModule, CKEditorModule],
    templateUrl: './manage-project.component.html',
    styleUrl: './manage-project.component.scss'
})
export class ManageProjectComponent implements OnInit {
    public Editor!: {
        create(sourceElementOrData: string | HTMLElement, config?: EditorConfig): Promise<ClassicEditor>;
        EditorWatchdog: any;
    };
    public config!: EditorConfig;

    public ngOnInit(): void {
        loadCKEditorCloud({
            version: '45.2.1',
            premium: true
        }).then(this._setupEditor.bind(this));
    }

    private _setupEditor(cloud: CKEditorCloudResult<{ version: '45.2.1'; premium: true }>) {
        const { ClassicEditor, Essentials, Paragraph, Bold, Italic } = cloud.CKEditor;

        this.Editor = ClassicEditor;
        this.config = {
            plugins: [Essentials, Paragraph, Bold, Italic],
            toolbar: ['bold', 'italic'],
            placeholder: 'Nhập nội dung báo cáo...'
        };
    }

    project = {
        lecturer: 'Nguyễn Văn Quyết',
        title: 'Xây dựng website tìm kiếm việc làm Công nghệ Thông tin'
    };

    public model = {
        name: 'Hardik',
        description: '<p>This is a sample form using CKEditor.</p>'
    };

    weeks = Array.from({ length: 20 }, (_, i) => ({ label: `Tuần ${i + 1}`, value: i + 1 }));
    selectedWeek = 17;
    startDate!: Date;
    endDate!: Date;

    task = {
        work: '',
        content: '',
        result: '',
        reportDetail: ''
    };

    rows = 10;
    currentPage = 0;

    myProjectReports = [
        {
            week: 'Tuần 17',
            content: '',
            reportLink: '',
            comment: '',
            score: ''
        },
        {
            week: 'Tuần 16',
            content: '',
            reportLink: '',
            comment: '',
            score: ''
        }
    ];
}
