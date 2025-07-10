import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../enviroments/environment';

@Injectable({ providedIn: 'root' })
export class TeacherService {
    private readonly apiUrl = `${environment.apiUrl}/teachers`;

    constructor(private http: HttpClient) {}

    getAllTeachers(): Observable<any> {
        return this.http.get<any>(this.apiUrl);
    }
}
