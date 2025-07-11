import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { SharedModule } from '../../../shared/shared.module';

import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
@Component({
    selector: 'app-forgot-password',
    imports: [SharedModule, ReactiveFormsModule, RouterModule],
    templateUrl: './forgot-password.component.html',
    styleUrl: './forgot-password.component.scss',
    providers: [MessageService]
})
export class ForgotPasswordComponent {
    form: FormGroup;
    email: string = '';

    constructor(
        private fb: FormBuilder,
        private messageService: MessageService,
        private http: HttpClient,
        private router: Router,
        private authService: AuthService
    ) {
        this.form = this.fb.group({
            email: ['', [Validators.required, Validators.email]]
        });
    }

    sendOtp(): void {
        this.email = this.form.value.email;

        this.authService.sendOtp(this.email).subscribe({
            next: () => {
                this.messageService.add({ severity: 'success', summary: 'OTP đã gửi', detail: 'Vui lòng kiểm tra email' });

                this.router.navigate(['/auth/verify-otp'], {
                    queryParams: { email: this.email }
                });
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Lỗi', detail: 'Không thể gửi OTP' });
            }
        });
    }
}
