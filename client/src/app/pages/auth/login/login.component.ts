import { Component, inject } from '@angular/core';
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
import { Observable } from 'rxjs';

@Component({
    selector: 'app-login',
    standalone: true,
    templateUrl: './login.component.html',
    imports: [ButtonModule, CheckboxModule, InputTextModule, PasswordModule, ReactiveFormsModule, RouterModule, RippleModule, AppFloatingConfigurator, CommonModule],
    styleUrls: ['./login.component.scss']
})
export class Login {
    loginForm: FormGroup;
    private _fb = inject(FormBuilder);
    private _router = inject(Router);
    private store = inject(Store);

    isLoading$: Observable<boolean>;
    error$: Observable<any>;

    constructor() {
        this.loginForm = this._fb.group({
            email: ['', [Validators.required]],
            password: ['', [PasswordValidator.strong]]
        });

        this.isLoading$ = this.store.select(selectAuthIsLoading);
        this.error$ = this.store.select(selectAuthError);
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
