import {Injectable} from '@angular/core';
import {Technology} from '../../ability/ability.component';
import {HttpClient} from '@angular/common/http';
import {EnvService} from "../env/env.service";

@Injectable({
  providedIn: 'root'
})
export class TechnologyService {

  constructor(private http: HttpClient, private env: EnvService) {
  }


  find(id: number) {
    return this.http.get<Technology>(`${this.env.apiUrl}/technologies/${id}`);
  }

  findAll() {
    return this.http.get<Technology[]>(`${this.env.apiUrl}/technologies/`);
  }

  update(technology: Technology) {
    return this.http.put(`${this.env.apiUrl}/${technology.id}`, technology);
  }

  delete(id) {
    return this.http.delete(`${this.env.apiUrl}/technologies/${id}`);
  }
}
