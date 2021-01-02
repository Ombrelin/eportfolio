import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Diploma} from '../../diplomas/diplomas.component';

@Injectable({
  providedIn: 'root'
})
export class DiplomasService {

  constructor(private http: HttpClient) {
  }

  create(d: Diploma) {
    return this.http.post<Diploma>(`/diplomas/`, d);
  }

  findAll() {
    return this.http.get<Diploma[]>(`/diplomas/`);
  }

  find(id: number) {
    return this.http.get<Diploma>(`/diplomas/${id}`);
  }

  update(d: Diploma) {
    return this.http.put(`/diplomas/${d.id}`, d);
  }

  delete(id: number) {
    return this.http.delete(`/diplomas/${id}`);
  }
}
