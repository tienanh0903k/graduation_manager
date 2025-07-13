import { Component, inject, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ProjectTopicService } from '../../core/services/project-topic.service';
import { SharedModule } from '../../shared/shared.module';
import { TextareaModule } from 'primeng/textarea';
import { StudentsService } from '../../core/services/students.service';
import { TeacherService } from '../../core/services/teacher.service';
import { isOwner } from '../../core/utils/permissions.util';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { selectAuthUserId } from '../../core/store/auth/auth.selectors';

@Component({
    selector: 'app-create-project',
    standalone: true,
    imports: [SharedModule, TextareaModule],
    templateUrl: './create-project.component.html',
    styleUrls: ['./create-project.component.scss'],
    providers: [MessageService]
})
export class CreateProjectComponent implements OnInit {
    isDialogVisible = false;
    isDetailDialogVisible = false;
    selectedTopic: any = null;
    currentUserId?: number;

    currentUserId$!: Observable<number | undefined>;

    private store = inject(Store);

    topics: any[] = [];
    teacherOptions: any[] = [];

    totalRecords = 0;
    currentPage = 0;
    rowsPerPage = 10;

    departments = Array.from({ length: 9 }, (_, i) => {
        const val = (125211 + i).toString();
        return { label: val, value: val };
    });

    selectedDepartment = '';
    selectedLecturer = '';
    searchTitle = '';

    form = {
        title: '',
        description: '',
        teacherId: 1
    };

    constructor(
        private projectTopicService: ProjectTopicService,
        private messageService: MessageService,
        private studentsService: StudentsService,
        private teacherService: TeacherService
    ) {}

    ngOnInit(): void {
        this.currentUserId$ = this.store.select(selectAuthUserId);
        this.currentUserId$.subscribe((id) => {
            this.currentUserId = id;
        });
        this._loadSearchResults();
        this._loadTeachers();
    }

    _loadSearchResults(): void {
        this.studentsService.searchStudentProjects(this.selectedDepartment || undefined, this.selectedLecturer || undefined, this.searchTitle || undefined, this.currentPage, this.rowsPerPage).subscribe({
            next: (res) => {
                this.topics = res.content; // nếu backend trả kiểu Page
                this.totalRecords = res.totalElements;
                console.log('Danh sách đề tài:', this.topics);
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
                this.teacherOptions = res.data.map((teacher: any) => ({
                    label: teacher.name,
                    value: teacher.name
                }));
            },
            error: (err) => console.error('Lỗi khi tải danh sách giảng viên:', err)
        });
    }

    onPageChange(event: any): void {
        this.currentPage = event.first / event.rows;
        this.rowsPerPage = event.rows;
        this._loadSearchResults();
    }

    submitProposal(): void {
        this.projectTopicService.createTopic(this.form).subscribe({
            next: () => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Đề tài đã được gửi thành công!'
                });
                this.closeProposeDialog();
                this.resetForm();

                // Reset bộ lọc nếu muốn
                this.selectedDepartment = '';
                this.selectedLecturer = '';
                this.searchTitle = '';

                this.currentPage = 0; // về trang đầu
                this._loadSearchResults();
            },
            error: (err) => {
                console.error('Error creating topic:', err);
                this.messageService.add({
                    severity: 'error',
                    summary: 'Lỗi',
                    detail: 'Sinh viên chỉ có thể đề xuất 1 đề tài mà thôi'
                });
            }
        });
    }

    editTopic(topic: any): void {
        this.selectedTopic = topic;
        this.isDialogVisible = true;
        this.form = {
            title: topic.tenDeTai,
            description: topic.moTa || '', // nếu có
            teacherId: topic.teacherId
        };
    }

    deleteTopic(topic: any): void {
        if (confirm('Bạn có chắc muốn xóa đề tài này?')) {
            // this.projectTopicService.deleteTopic(topic.id).subscribe({
            //   next: () => {
            //     this.messageService.add({ severity: 'success', summary: 'Thành công', detail: 'Đã xóa đề tài.' });
            //     this._loadSearchResults();
            //   },
            //   error: () => {
            //     this.messageService.add({ severity: 'error', summary: 'Lỗi', detail: 'Không thể xóa đề tài.' });
            //   }
            // });
        }
    }

    openProposeDialog(): void {
        this.isDialogVisible = true;
    }

    openDetailDialog(topic: any): void {
        this.selectedTopic = topic;
        this.isDetailDialogVisible = true;
    }

    closeProposeDialog(): void {
        this.isDialogVisible = false;
    }

    resetForm(): void {
        this.form = {
            title: '',
            description: '',
            teacherId: 1
        };
    }

    filterTopics(): void {
        this.currentPage = 0;
        this._loadSearchResults();
    }

    //access edit delete
    canEdit(topic: any): boolean {
        return this.currentUserId != null && topic.id === this.currentUserId;
    }

    canDelete(topic: any): boolean {
        return this.currentUserId != null && topic.id === this.currentUserId;
    }
}
