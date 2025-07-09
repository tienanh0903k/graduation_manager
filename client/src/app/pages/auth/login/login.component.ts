import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { RippleModule } from 'primeng/ripple';
import { AppFloatingConfigurator } from '../../../layout/component/app.floatingconfigurator';
import { PasswordValidator } from '../../../shared/validators/password.validator';
import { CommonModule } from '@angular/common';
import { Store } from '@ngrx/store';
import * as AuthActions from '../../../core/store/auth/auth.actions';
import { selectAuthError, selectAuthIsLoading } from '../../../core/store/auth/auth.selectors';
import { filter, Observable } from 'rxjs';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
    selector: 'app-login',
    standalone: true,
    templateUrl: './login.component.html',
    imports: [ButtonModule, CheckboxModule, InputTextModule, PasswordModule, ReactiveFormsModule, RouterModule, ToastModule, RippleModule, AppFloatingConfigurator, CommonModule, ProgressSpinnerModule],
    styleUrls: ['./login.component.scss'],
    providers: [MessageService]
})
export class Login implements OnInit {
    loginForm: FormGroup;
    private _fb = inject(FormBuilder);
    private _router = inject(Router);
    private store = inject(Store);

    isLoading$: Observable<boolean>;
    error$: Observable<any>;

    constructor(private messageService: MessageService) {

        this.loginForm = this._fb.group({
            email: ['', [Validators.required]],
            password: ['', [PasswordValidator.strong]]
        });

        this.isLoading$ = this.store.select(selectAuthIsLoading);
        this.error$ = this.store.select(selectAuthError);

        this.error$
            .pipe(filter((err) => !!err))
            .subscribe((error) => {
                const detail = error?.error?.message || 'Sai tài khoản hoặc mật khẩu';
                this.messageService.add({
                    severity: 'error',
                    summary: 'Đăng nhập thất bại',
                    detail
                });
            });

    }

    ngOnInit(): void {
         this.messageService.add({
    severity: 'success',
    summary: 'Test',
    detail: 'Toast đang hoạt động!'
  });
    }

    login(): void {
        if (this.loginForm.valid) {
            this.store.dispatch(AuthActions.login({ request: this.loginForm.value }));

        }
    }

    get passwordError(): string | null {
        const control = this.loginForm.get('password');
        if (control?.hasError('passwordStrength') && control?.dirty) {
            return control.getError('passwordStrength').message;
        }
        return null;
    }
}
