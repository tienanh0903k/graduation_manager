import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { RippleModule } from 'primeng/ripple';
import { AppFloatingConfigurator } from '../../../layout/component/app.floatingconfigurator';
import { AuthService } from '../../../core/services/auth.service';

@Component({
    selector: 'app-login',
    standalone: true,
    templateUrl: './login.component.html',
    imports: [ButtonModule, CheckboxModule, InputTextModule, PasswordModule, ReactiveFormsModule, RouterModule, RippleModule, AppFloatingConfigurator],
    styleUrls: ['./login.component.scss']
})
export class Login {
    loginForm: FormGroup;
    private _fb = inject(FormBuilder);
    private _authService = inject(AuthService);
    private _router = inject(Router);

    constructor() {
        this.loginForm = this._fb.group({
            username: ['', [Validators.required]],
            password: ['', [Validators.required]]
        });
    }

    login(): void {
        if (this.loginForm.valid) {
            this._authService.login(this.loginForm.value).subscribe(() => {
                this._router.navigate(['/']);
            });
        }
    }
}
