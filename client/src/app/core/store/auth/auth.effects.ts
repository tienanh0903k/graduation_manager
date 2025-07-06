import { Injectable, inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import * as AuthActions from './auth.actions';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';

@Injectable()
export class AuthEffects {
    private actions$ = inject(Actions);
    private authService = inject(AuthService);
    private router = inject(Router);

    login$ = createEffect(() =>
        this.actions$.pipe(
            ofType(AuthActions.login),
            switchMap((action) =>
                this.authService.login(action.request).pipe(
                    map((response) => AuthActions.loginSuccess({ response })),
                    catchError((error) => of(AuthActions.loginFailure({ error })))
                )
            )
        )
    );

    loginSuccess$ = createEffect(
        () =>
            this.actions$.pipe(
                ofType(AuthActions.loginSuccess),
                tap(({ response }) => {
                    this.authService.setAuthData(response);
                    this.router.navigate(['/']);
                })
            ),
        { dispatch: false }
    );



    // autoLogin$ = createEffect(() =>
    //     this.actions$.pipe(
    //         ofType(AuthActions.autoLogin),
    //         switchMap(() => {
    //             const token = localStorage.getItem('auth_token');
    //             const user = JSON.parse(localStorage.getItem('user') || 'null');

    //             if (token && user) {
    //                 return of(AuthActions.loginSuccess({
    //                     response: {
    //                         token,
    //                         userDto: user,
    //                         menuPermissions: JSON.parse(localStorage.getItem('menuPermissions') || 'null'),
    //                         message: 'Tự động đăng nhập thành công'
    //                     }
    //                 }));
    //             }
    //             return of(AuthActions.autoLoginFailure());
    //         })
    //     )
    // );
}
