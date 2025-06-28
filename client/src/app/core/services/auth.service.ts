import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationRequest } from '../models/authentication-request.model';
import { AuthenticationResponse } from '../models/authentication-response.model';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private authUrl = 'http://localhost:8080/auth';

    constructor(private http: HttpClient) {}

    login(request: AuthenticationRequest): Observable<AuthenticationResponse> {
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
}
