import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { IMenuItem } from "../../pages/admin-system/menus/menus.component";

@Injectable({
    providedIn: 'root'
})
export class MenuItemsService {
    private readonly apiUrl = 'http://localhost:8080/menu';

    constructor(private http: HttpClient) {}

    getAll(): Observable<IMenuItem[]> {
        return this.http.get<IMenuItem[]>(this.apiUrl);
    }

    getById(id: number): Observable<IMenuItem> {
        return this.http.get<IMenuItem>(`${this.apiUrl}/${id}`);
    }

    create(menu: IMenuItem): Observable<IMenuItem> {
        return this.http.post<IMenuItem>(this.apiUrl, menu);
    }

    update(id: number, menu: IMenuItem): Observable<IMenuItem> {
        return this.http.put<IMenuItem>(`${this.apiUrl}/${id}`, menu);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
