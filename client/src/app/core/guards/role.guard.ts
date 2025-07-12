import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable, of } from 'rxjs';
import { catchError, filter, map, take } from 'rxjs/operators';
import { selectUserRole } from '../store/auth/auth.selectors';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private store: Store, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    const requiredRole = route.data['role'] as string;
    const currentUrl = state.url;

    return this.store.select(selectUserRole).pipe(
      filter(role => role !== null),
      take(1),
      map(role => {
        console.log('[RoleGuard] current role:', role, '| required:', requiredRole, '| path:', currentUrl);

        if (role === requiredRole) {
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
