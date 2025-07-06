// my-project.component.ts
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { DividerModule } from 'primeng/divider';
import { ToastModule } from 'primeng/toast';
import { TagModule } from 'primeng/tag';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-my-project',
  templateUrl: './my-project.component.html',
    imports: [
        InputTextModule,
        ButtonModule,
        DividerModule,
        ToastModule,
        TagModule,
        FormsModule,
        CommonModule
    ],
  styleUrls: ['./my-project.component.scss'],
  providers: [MessageService]
})
export class MyProjectComponent implements OnInit {
  project: any;
  form = {
    proposedTitle: '',
    proposedDescription: ''
  };

  constructor(private messageService: MessageService) {}

  ngOnInit(): void {
    // 🚀 Giả lập gọi API lấy dữ liệu project của sinh viên
    this.project = {
      originalTitle: 'Xây dựng hệ thống quản lý đồ án tốt nghiệp',
      originalDescription: 'Ứng dụng web giúp sinh viên đề xuất, chỉnh sửa và nộp báo cáo theo tuần.',
      teacherName: 'TS. Nguyễn Văn Quyết',
      status: 'PENDING', // APPROVED | REJECTED
      statusText: 'Chờ duyệt',
      isApproved: false,
      feedback: 'Mô tả chưa rõ ràng, cần bổ sung mục tiêu cụ thể.'
    };
  }

  submitEditRequest(): void {
    if (!this.form.proposedTitle || !this.form.proposedDescription) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Thiếu thông tin',
        detail: 'Vui lòng điền đầy đủ Tên đề tài và Mô tả.'
      });
      return;
    }

    // Gửi dữ liệu chỉnh sửa đến backend
    // this.projectService.submitEditRequest(this.form).subscribe(...);

    this.messageService.add({
      severity: 'success',
      summary: 'Gửi thành công',
      detail: 'Yêu cầu chỉnh sửa đề tài đã được gửi đến giảng viên.'
    });

    // Optionally clear form
    this.form = {
      proposedTitle: '',
      proposedDescription: ''
    };
  }
}
