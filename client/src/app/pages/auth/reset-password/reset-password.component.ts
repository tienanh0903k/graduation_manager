import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { BehaviorSubject } from 'rxjs';
import { PasswordModule } from 'primeng/password';

@Component({
    selector: 'app-reset-password',
    imports: [SharedModule, RouterModule, ReactiveFormsModule, PasswordModule],
    templateUrl: './reset-password.component.html',
    styleUrls: ['./reset-password.component.scss'],
    standalone: true,

})
export class ResetPasswordComponent {
    passwordResetForm: FormGroup;
    passwordError: string | null = null;
    // Sử dụng BehaviorSubject để quản lý trạng thái loading
    private isLoadingSubject = new BehaviorSubject<boolean>(false);
    isLoading$ = this.isLoadingSubject.asObservable();

    constructor(
        private fb: FormBuilder,
        private router: Router,
        private authService: AuthService
    ) {
        this.passwordResetForm = this.fb.group(
            {
                newPassword: ['', [Validators.required, Validators.minLength(8)]],
                confirmPassword: ['', Validators.required]
            },
            { validator: this.passwordMatchValidator }
        );
    }

    passwordMatchValidator(form: FormGroup): { [key: string]: boolean } | null {
        if (form.get('newPassword')?.value !== form.get('confirmPassword')?.value) {
            return { passwordMismatch: true };
        }
        return null;
    }

    resetPassword() {
        if (this.passwordResetForm.invalid) {
            return;
        }
        const { newPassword } = this.passwordResetForm.value;
        this.isLoadingSubject.next(true); // Bắt đầu loading
        this.authService.resetPassword(newPassword).subscribe({
            next: () => {
                this.isLoadingSubject.next(false); // Kết thúc loading
                this.router.navigate(['/auth/login']);
            },
            error: (err) => {
                this.isLoadingSubject.next(false); // Kết thúc loading
                this.passwordError = err.error.message;
                console.error('Error resetting password:', err);
            }
        });
    }
}
