import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../enviroments/environment';
import { Observable } from 'rxjs';
import { PaginatedResponse } from '../models/PaginatedResponse.model';

export interface StudentProjectList {
    tenGiaoVien: string;
    tenDeTai: string;
    tenSinhVien: string;
    maSinhVien: string;
    lop: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

export interface Student {
  id: number;
  mssv: string;
  classCode: string;
  user: {
    id: number;
    name: string;
    email: string;
  };
}

@Injectable({ providedIn: 'root' })
export class StudentsService {
    private readonly apiUrl = `${environment.apiUrl}/student`;

    constructor(private http: HttpClient) {}

    searchStudentProjects(classCode?: string, teacherName?: string, title?: string, page: number = 0, size: number = 10): Observable<PaginatedResponse<StudentProjectList>> {
        let params = new HttpParams().set('page', page.toString()).set('size', size.toString());

        if (classCode) {
            params = params.set('classCode', classCode);
        }
        if (teacherName) {
            params = params.set('teacherName', teacherName);
        }
        if (title) {
            params = params.set('title', title);
        }

        return this.http.get<PaginatedResponse<StudentProjectList>>(`${this.apiUrl}/search`, { params });
    }

    /**
     * import excel
     */
    importStudents(students: any[]): Observable<any> {
        const headers = new HttpHeaders().set('Content-Type', 'application/json');
        return this.http.post(`${this.apiUrl}/import`, students, { headers });
    }

    /**
     * get all student for admin
     */
    search(criteria: any, page: number, size: number, sort?: string): Observable<PageResponse<Student>> {
        let params = new HttpParams().set('page', page.toString()).set('size', size.toString());
        if (sort) params = params.set('sort', sort);
        return this.http.post<PageResponse<Student>>(`${this.apiUrl}/search-list`, criteria, { params });
    }
}
