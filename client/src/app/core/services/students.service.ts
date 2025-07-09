import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../enviroments/environment';
import { Observable } from 'rxjs';

export interface StudentProjectList {
    tenGiaoVien: string;
    tenDeTai: string;
    tenSinhVien: string;
    maSinhVien: string;
    lop: string;
}

@Injectable({ providedIn: 'root' })
export class StudentsService {
    private readonly apiUrl = `${environment.apiUrl}/student`;

    constructor(private http: HttpClient) {}

    searchStudentProjects(classCode?: string, teacherName?: string, title?: string, page: number = 0, size: number = 10): Observable<StudentProjectList[]> {
        let params = new HttpParams()
                                    .set('page', page.toString())
                                    .set('size', size.toString());

        if (classCode) {
            params = params.set('classCode', classCode);
        }
        if (teacherName) {
            params = params.set('teacherName', teacherName);
        }
        if (title) {
            params = params.set('title', title);
        }

        return this.http.get<StudentProjectList[]>(`${this.apiUrl}/search`, { params });
    }
}
