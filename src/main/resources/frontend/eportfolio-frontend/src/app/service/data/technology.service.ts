import {Injectable} from '@angular/core';
import {Technology} from '../../ability/ability.component';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TechnologyService {

  constructor(private http: HttpClient) {
  }


  find(id: number) {
    return this.http.get<Technology>(`/technologies/${id}`);
  }

  findAll() {
    return this.http.get<Technology[]>(`/technologies/`);
  }

  update(technology: Technology) {
    return this.http.put(`/${technology.id}`, technology);
  }

  delete(id) {
    return this.http.delete(`/technologies/${id}`);
  }
}
