import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationRequest } from '../models/authentication-request.model';
import { AuthenticationResponse } from '../models/authentication-response.model';
import { User } from '../models/user.model';
import { MenuPermissionResponseDTO } from '../models/MenuPermission/MenuPermissionResponseDTO';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private authUrl = 'http://localhost:8080/auth';

    constructor(private http: HttpClient) {}

    login(request: AuthenticationRequest): Observable<AuthenticationResponse> {
        console.log('Login request:', request);
        return this.http.post<AuthenticationResponse>(`${this.authUrl}/login`, request);
    }

    logout(): void {
        localStorage.removeItem('token');
    }

    public getToken(): string | null {
        return localStorage.getItem('token');
    }

    public hasToken(): boolean {
        return !!this.getToken();
    }

    public setAuthData(response: AuthenticationResponse): void {
        localStorage.setItem('token', response.token);
        localStorage.setItem('userDto', JSON.stringify(response.userDto));
        localStorage.setItem('menuPermissions', JSON.stringify(response.menuPermissions));
    }

     public getUser(): User | null {
        const data = localStorage.getItem('userDto');
        return data ? JSON.parse(data) : null;
    }

    public getMenuPermissions(): MenuPermissionResponseDTO[] | null {
        const data = localStorage.getItem('menuPermissions');
        return data ? JSON.parse(data) : null;
    }

}
