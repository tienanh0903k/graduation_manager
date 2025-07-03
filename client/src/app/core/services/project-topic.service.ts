import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../enviroments/environment';
import { Observable } from 'rxjs';

export interface CreateProjectTopicPayload {
    title: string;
    description: string;
    teacherId: number;
}

@Injectable({ providedIn: 'root' })
export class ProjectTopicService {
    private apiUrl = `${environment.apiUrl}/project-topics`;

    constructor(private http: HttpClient) {}

    createTopic(payload: CreateProjectTopicPayload) {
        return this.http.post(this.apiUrl, payload);
    }

    getAll(): Observable<any> {
        return this.http.get(this.apiUrl);
    }
}
