import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Project} from '../../project/project.component';
import {Ability, Technology} from '../../ability/ability.component';
import {EnvService} from "../env/env.service";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient, private env: EnvService) {
  }

  find(id: number) {
    return this.http.get<Project>(`${this.env.apiUrl}/projects/${id}`);
  }

  findAll() {
    return this.http.get<Project[]>(`${this.env.apiUrl}/projects/`);
  }

  update(p: Project) {
    return this.http.put(`${this.env.apiUrl}/projects/${p.id}`, p);
  }

  addAbility(id: number, a: Ability) {
    return this.http.post(`${this.env.apiUrl}/projects/${id}/abilities`, a);
  }

  removeAbility(id: number, idAbility: number) {
    return this.http.delete(`${this.env.apiUrl}/projects/${id}/abilities/${idAbility}`);
  }

  addTechnology(id: number, t: Technology) {
    return this.http.post(`${this.env.apiUrl}/projects/${id}/technologies`, t);
  }

  removeTechnology(id: number, idTechnology: number) {
    return this.http.delete(`${this.env.apiUrl}/projects/${id}/technologies/${idTechnology}`);
  }

  delete(id: number) {
    return this.http.delete(`${this.env.apiUrl}/projects/${id}`);
  }
}
