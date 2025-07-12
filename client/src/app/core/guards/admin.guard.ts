    import { Injectable } from '@angular/core';
    import { CanActivate, Router } from '@angular/router';
    import { Store } from '@ngrx/store';
    import { Observable, of } from 'rxjs';
    import { catchError, filter, map, take } from 'rxjs/operators';
    import { selectUserRole } from '../store/auth/auth.selectors';

    @Injectable({
        providedIn: 'root'
    })
    export class AdminGuard implements CanActivate {
        constructor(
            private store: Store<any>,
            private router: Router
        ) {}

        canActivate(): Observable<boolean> {
            return this.store.select(selectUserRole).pipe(
                filter((role) => role !== null),
                take(1),
                map((role) => {
                    console.log('[AdminGuard] checking role:', role);
                    if (role === 'ADMIN') {
                        return true;
                    }

                    this.router.navigate(['/auth/access']);
                    return false;
                }),
                catchError(() => {
                    this.router.navigate(['/auth/login']);
                    return of(false);
                })
            );
        }
    }
