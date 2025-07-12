import { Component, OnInit } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { SharedModule } from '../../shared/shared.module';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [SharedModule, InputTextModule, ButtonModule],
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.scss'
})
export class ProfilePageComponent implements OnInit {
  profile = {
    name: 'Nguyễn Văn A',
    email: 'vana@student.edu.vn',
    role: 1, // STUDENT
    studentInfo: {
      mssv: 'SV123456',
      class_name: 'CNTT K65A'
    }
  };

  ngOnInit(): void {
    // Không cần API, dùng sẵn dữ liệu mock
  }
}
