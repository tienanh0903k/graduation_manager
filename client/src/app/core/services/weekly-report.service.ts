import { Injectable } from "@angular/core";
import { environment } from "../../../enviroments/environment";
import { Observable } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";

@Injectable({ providedIn: 'root' })
export class WeeklyReportService {
        private readonly apiUrl = `${environment.apiUrl}/reports`;

        constructor(private http: HttpClient) {}

        getReportsByDate(date: string): Observable<any[]> {
        const params = new HttpParams().set('date', date);
        return this.http.get<any[]>(`${this.apiUrl}/user`, { params });
    }
}
