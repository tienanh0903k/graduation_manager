import { HttpClient, HttpHeaders } from '@angular/common/http';
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
    private readonly apiUrl = `${environment.apiUrl}/project-topics`;

    constructor(private http: HttpClient) {}

    public getAuthHeaders(): HttpHeaders {
        const rawAuth = localStorage.getItem('auth');
        if (!rawAuth) {
            throw new Error('Authentication token not found');
        }

        try {
            const { token } = JSON.parse(rawAuth);
            if (!token) {
                throw new Error('Token is missing in auth data');
            }

            return new HttpHeaders({
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`
            });
        } catch {
            throw new Error('Invalid auth data format');
        }
    }

    /**
     * Gửi yêu cầu tạo đề tài dự án mới.
     *
     * @param payload Dữ liệu đầu vào bao gồm:
     *  - `title`: Tiêu đề đề tài
     *  - `description`: Mô tả đề tài
     *  - `teacherId`: ID của giảng viên hướng dẫn
     *
     * @returns Observable phản hồi từ server sau khi tạo đề tài
     *
     * @throws Error nếu thiếu trường bắt buộc trong payload hoặc không có token xác thực
     */
    createTopic(payload: CreateProjectTopicPayload): Observable<any> {
        const { title, description, teacherId } = payload;

        if (!title || !description || !teacherId) {
            throw new Error('Payload must include title, description, and teacherId');
        }

        const headers = this.getAuthHeaders();
        return this.http.post(this.apiUrl, payload, { headers });
    }

    /**
     * Lấy danh sách tất cả các đề tài dự án.
     * @returns list of all project topics
     */
    getAll(): Observable<any> {
        return this.http.get(this.apiUrl);
    }
}
