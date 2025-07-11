import { Component, OnInit, OnDestroy } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { InputOtpModule } from 'primeng/inputotp';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { AuthService } from '../../../core/services/auth.service';

@Component({
    selector: 'app-otp-verify',
    imports: [SharedModule, InputOtpModule, RouterModule, ToastModule],
    templateUrl: './otp-verify.component.html',
    styleUrl: './otp-verify.component.scss',
    standalone: true,
    providers: [MessageService]
})
export class OtpVerifyComponent implements OnInit, OnDestroy {
    otpCode: string = '';
    email: string = '';
    countdown: number = 300;
    intervalId: any;
    isLoading: boolean = false;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private messageService: MessageService,
        private authService: AuthService
    ) {
        this.route.queryParams.subscribe((params) => {
            this.email = params['email'] || '';
        });
    }

    ngOnInit(): void {
        this.startCountdown();
    }

    ngOnDestroy(): void {
        clearInterval(this.intervalId);
    }

    startCountdown(): void {
        this.intervalId = setInterval(() => {
            if (this.countdown > 0) {
                this.countdown--;
            } else {
                clearInterval(this.intervalId);
                this.messageService.add({
                    severity: 'warn',
                    summary: 'Hết thời gian',
                    detail: 'Mã OTP đã hết hạn'
                });
            }
        }, 1000);
    }

    get formattedTime(): string {
        const minutes = Math.floor(this.countdown / 60);
        const seconds = this.countdown % 60;
        return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    }

    verifyOtp(): void {
        if (this.otpCode.length !== 6) {
            this.messageService.add({
                severity: 'warn',
                summary: 'Lỗi nhập liệu',
                detail: 'Mã OTP chưa đủ 6 chữ số'
            });
            return;
        }

        this.isLoading = true;

        this.authService.verifyOtp(this.email, this.otpCode).subscribe({
            next: (response) => {
                this.isLoading = false;
                this.messageService.add({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Xác minh OTP thành công'
                });

                // Dừng countdown khi verify thành công
                clearInterval(this.intervalId);

                this.router.navigate(['/auth/reset-password'], {
                    queryParams: { email: this.email }
                });
            },
            error: (error) => {
                this.isLoading = false;
                console.error('Verify OTP error:', error);

                // Xử lý các loại lỗi khác nhau
                let errorMessage = 'Có lỗi xảy ra, vui lòng thử lại';

                if (error.status === 400) {
                    errorMessage = 'Mã OTP không đúng hoặc đã hết hạn';
                } else if (error.status === 404) {
                    errorMessage = 'Không tìm thấy email này';
                } else if (error.error?.message) {
                    errorMessage = error.error.message;
                }

                this.messageService.add({
                    severity: 'error',
                    summary: 'Lỗi xác minh',
                    detail: errorMessage
                });

                this.otpCode = '';
            }
        });
    }
}
