import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../enviroments/environment';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private readonly apiUrl = 'http://localhost:8080/menu';

  constructor(private http: HttpClient) {}

  /**
   * Gọi: GET /menu/{roleName}/menus
   */
  getMenusByRole(roleName: string): Observable<{ assigned: any[]; unassigned: any[] }> {
    return this.http.get<{ assigned: any[]; unassigned: any[] }>(
      `${this.apiUrl}/${roleName}/menus`
    );
  }

  /**
   * Gọi: PUT /menu/{roleName}/menus
   */
  updateMenusForRole(roleName: string, menuIds: number[]): Observable<any> {
    return this.http.put(`${this.apiUrl}/${roleName}/menus`, { menuIds });
  }

  /**
   * Optional: nếu bạn có API lấy danh sách role
   * Ví dụ: GET /roles
   */
  getAllRoles(): Observable<any[]> {
    return this.http.get<any[]>(`${environment.apiUrl}/roles`);
  }
}
