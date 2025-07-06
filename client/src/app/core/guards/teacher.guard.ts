import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { selectAuthUser } from '../store/auth/auth.selectors';  // Đảm bảo bạn đã import đúng selector

@Injectable({
  providedIn: 'root'
})
export class TeacherGuard implements CanActivate {

  constructor(private store: Store, private router: Router) { }

  canActivate(): Observable<boolean> {
    return this.store.select(selectAuthUser).pipe(
      take(1),
      map(user => {
        if (user && user.role === 'TEACHER') {
          return true;
        } else {
          this.router.navigate(['/auth/access']);
          return false;
        }
      })
    );
  }
}
