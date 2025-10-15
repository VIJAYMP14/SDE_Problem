import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class GameService {
  private api = 'http://localhost:8080/api/2048';
  constructor(private http: HttpClient) {}

  getBoard() { return this.http.get<any>(`${this.api}/board`); }
  move(direction: string) { return this.http.post<any>(`${this.api}/move/${direction}`, {}); }
  restart() { return this.http.post(`${this.api}/restart`, {}); }
}
