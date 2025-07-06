import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable, of } from 'rxjs';
import { catchError, filter, map, take, timeout } from 'rxjs/operators';
import { selectAuthUser, selectUserRole } from '../store/auth/auth.selectors';

@Injectable({
    providedIn: 'root'
})
export class TeacherGuard implements CanActivate {
    constructor(
        private store: Store<any>,
        private router: Router
    ) {}

    canActivate(): Observable<boolean> {
        return this.store.select(selectUserRole).pipe(
            timeout(2000),
            filter((role) => role !== null),
            take(1),
            map((role) => {
                console.log('User role in TeacherGuard:', role); // Debugging

                if (role === 'TEACHER') {
                    return true;
                }

                // Redirect to access denied or login page
                this.router.navigate(['/auth/access']);
                return false;
            }),
            catchError((error) => {
                console.error('Error in TeacherGuard:', error);
                this.router.navigate(['/auth/login']);
                return of(false);
            })
        );
    }
}
