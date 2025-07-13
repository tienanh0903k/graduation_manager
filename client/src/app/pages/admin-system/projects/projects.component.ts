import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { TableModule } from 'primeng/table';
import { StudentsService } from '../../../core/services/students.service';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { TeacherService } from '../../../core/services/teacher.service';
import { MultiSelectModule } from 'primeng/multiselect';
import { ReviewService } from '../../../core/services/review.service';
import { map } from 'rxjs';
import { groupReviewerName } from '../../../core/utils/lodash/groupReviewerName';

@Component({
    selector: 'app-projects',
    imports: [SharedModule, TableModule, ToastModule, MultiSelectModule],
    templateUrl: './projects.component.html',
    styleUrl: './projects.component.scss',
    providers: [MessageService]
})
export class ProjectsComponent {
    selectedReviewers: number[] = [];

    topics: any[] = [];
    totalRecords = 0;
    currentPage = 0;
    rowsPerPage = 10;

    selectedDepartment: string | null = null;
    selectedLecturer: string | null = null;
    searchTitle: string = '';

    teacherOptions: any[] = [];
    reviewerOptions: any[] = [];

    constructor(
        private studentsService: StudentsService,
        private messageService: MessageService,
        private teacherService: TeacherService,
        private reviewService: ReviewService
    ) {}

    ngOnInit() {
        this._loadSearchResults();
        this._loadTeachers();
    }

    _loadSearchResults(): void {
        this.studentsService
            .searchStudentProjects(this.selectedDepartment || undefined, this.selectedLecturer || undefined, this.searchTitle || undefined, this.currentPage, this.rowsPerPage)
            .pipe(
                map((res) => {
                    return { ...res, content: groupReviewerName(res.content) };
                })
            )
            .subscribe({
                next: (res) => {
                    this.topics = res.content;
                    console.log('thissss', this.topics);

                    this.totalRecords = res.totalElements;
                },
                error: () =>
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Lỗi',
                        detail: 'Không thể tìm kiếm đề tài. Vui lòng thử lại.'
                    })
            });
    }

    _loadTeachers(): void {
        this.teacherService.getAllTeachers().subscribe({
            next: (res) => {
                const options = res.data.map((teacher: any) => ({
                    label: teacher.name,
                    value: teacher.id
                }));
                this.teacherOptions = options;
                this.reviewerOptions = options;
            },
            error: (err) => {
                console.error('Lỗi khi tải danh sách giảng viên:', err);
                this.messageService.add({
                    severity: 'error',
                    summary: 'Lỗi',
                    detail: 'Không thể tải danh sách giảng viên'
                });
            }
        });
    }

    onPageChange(event: any) {
        this.currentPage = event.first / event.rows;
        this.rowsPerPage = event.rows;
        this._loadSearchResults();
    }

    departments = [
        { label: '125211', value: '125211' },
        { label: '125212', value: '125212' },
        { label: '125213', value: '125213' }
    ];

    selectedTopics: any[] = [];
    selectedReviewer: number | null = null;

    councilOptions = [
        { label: 'Hội đồng 1', value: 301 },
        { label: 'Hội đồng 2', value: 302 }
    ];
    selectedCouncil: number | null = null;

    assignReviewerAndCouncil() {
        const projectIds = this.selectedTopics.map((topic) => topic.id);

        const req = {
            projectIds,
            reviewerId: this.selectedReviewers,
            councilId: this.selectedCouncil
        };

        this.reviewService.assignReviewerAndCouncil(req).subscribe({
            next: () => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Gán phản biện với hội đồng thành công'
                });
                this.selectedTopics = [];
                this.selectedReviewers = [];
                this.selectedCouncil = null;
            },
            error: () => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Lỗi',
                    detail: 'Không thể gán phản biện với hội đồng'
                });
            }
        });

        // console.log('Data gửi lên backend:', req);
    }
}
