import { HttpRequest, HttpHandlerFn, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

export const errorInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
    const authService = inject(AuthService);
    const router = inject(Router);
    return next(req).pipe(
        catchError((error: any) => {
            if (error instanceof HttpErrorResponse && error.status === 401) {
                authService.logout();
                router.navigate(['/auth/login']);
            }
            return throwError(() => error);
        })
    );
};
