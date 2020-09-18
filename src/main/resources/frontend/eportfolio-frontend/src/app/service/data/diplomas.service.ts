import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Diploma} from '../../diplomas/diplomas.component';
import {EnvService} from "../env/env.service";

@Injectable({
  providedIn: 'root'
})
export class DiplomasService {

  constructor(private http: HttpClient, private env: EnvService) {
  }

  create(d: Diploma) {
    return this.http.post<Diploma>(`${this.env.apiUrl}/diplomas/`, d);
  }

  findAll() {
    return this.http.get<Diploma[]>(`${this.env.apiUrl}/diplomas/`);
  }

  find(id: number) {
    return this.http.get<Diploma>(`${this.env.apiUrl}/diplomas/${id}`);
  }

  update(d: Diploma) {
    return this.http.put(`${this.env.apiUrl}/diplomas/${d.id}`, d);
  }

  delete(id: number) {
    return this.http.delete(`${this.env.apiUrl}/diplomas/${id}`);
  }
}
