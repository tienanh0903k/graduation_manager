import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class ReviewService {
    private readonly apiUrl = 'http://localhost:8080/api/reviewer';

    constructor(private http: HttpClient) {}

    assignReviewerAndCouncil(requestBody: any) {
        return this.http.post(`${this.apiUrl}/assign-reviewers-council`, requestBody);
    }

}
