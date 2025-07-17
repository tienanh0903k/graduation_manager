import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../enviroments/environment';
import { ProjectTopicService } from './project-topic.service';

@Injectable({ providedIn: 'root' })
export class TeacherService {
    private readonly apiUrl = `${environment.apiUrl}/teachers`;

    constructor(
        private http: HttpClient,
        private projectTopicService: ProjectTopicService
    ) {}

    getAllTeachers(): Observable<any> {
        return this.http.get<any>(this.apiUrl);
    }

    /**
     * get project by teacher
     */

    getApprovedProjects(keyword: string = '', isApproved: boolean = true): Observable<any[]> {
        const headers = this.projectTopicService.getAuthHeaders();
        return this.http.get<any[]>(`${this.apiUrl}/project-topics?isApproved=${isApproved}&keyword=${keyword}`, { headers });
    }

    /**
     * approve project
     */
    approveProject(projectId: number): Observable<any> {
        return this.http.patch(`${this.apiUrl}/projects/${projectId}/approve`, {});
    }
}
