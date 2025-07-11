import { Routes } from '@angular/router';
import { Access } from './access';
import { Error } from './error';
import { Login } from './login/login.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { OtpVerifyComponent } from './otp-verify/otp-verify.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';

export default [
    { path: 'access', component: Access },
    { path: 'error', component: Error },
    {
        path: 'forgot-password',
        component: ForgotPasswordComponent
    },
    {
        path: 'verify-otp',
        component: OtpVerifyComponent
    },
    {
        path: 'reset-password',
        component: ResetPasswordComponent
    },
    { path: 'login', component: Login }
] as Routes;
