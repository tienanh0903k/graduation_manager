import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationRequest } from '../models/authentication-request.model';
import { AuthenticationResponse } from '../models/authentication-response.model';
import { User } from '../models/user.model';
import { MenuPermissionResponseDTO } from '../models/MenuPermission/MenuPermissionResponseDTO';
import { environment } from '../../../enviroments/environment';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private readonly apiUrl = 'http://localhost:8080/auth';

    constructor(private http: HttpClient) {}

    login(request: AuthenticationRequest): Observable<AuthenticationResponse> {
        console.log('Login request:', request);
        return this.http.post<AuthenticationResponse>(`${this.apiUrl}/login`, request);
    }

    logout(): void {
        localStorage.removeItem('auth');
    }

    public getToken(): string | null {
        const raw = localStorage.getItem('auth');
        if (!raw) return null;

        try {
            const parsed = JSON.parse(raw);
            return parsed?.token ?? null;
        } catch {
            return null;
        }
    }

    public hasToken(): boolean {
        return !!this.getToken();
    }

    public setAuthData(response: AuthenticationResponse): void {
        const authState = {
            token: response.token,
            userDto: response.userDto,
            menuPermissions: response.menuPermissions,
            message: response.message ?? null,
            isLoading: false,
            error: null
        };
        localStorage.setItem('auth', JSON.stringify(authState));
    }

    public getUser(): User | null {
        const data = localStorage.getItem('userDto');
        return data ? JSON.parse(data) : null;
    }

    public getMenuPermissions(): MenuPermissionResponseDTO[] | null {
        const data = localStorage.getItem('menuPermissions');
        return data ? JSON.parse(data) : null;
    }


    /**
     *  HÀM XỬ LÝ RESET PASSWORD
     * @param email
     * @returns
     */
    public sendOtp(email: string): Observable<any> {
        return this.http.post(`${this.apiUrl}/send-otp`, null, {
            params: { email }
        });
    }
    //send with cookie
    public verifyOtp(email: string, otpCode: string): Observable<any> {
        return this.http.post(`${this.apiUrl}/verify-otp`, null, {
            params: {
                email: email,
                otpCode: otpCode
            },
            withCredentials: true
        });
    }



    /**
     * ======================================= QUẢN LÝ RESET TOKEN =======================================
     */
    public resetPassword(newPassword: string): Observable<any> {
        return this.http.post(`${this.apiUrl}/reset-password`, { newPassword }, {
            withCredentials: true
        });
    }

}
