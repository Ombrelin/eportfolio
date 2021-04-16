import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Project} from '../../components/project/project.component';
import {Ability, Technology} from '../../components/ability/ability.component';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient) {
  }

  find(id: number) {
    return this.http.get<Project>(`/projects/${id}`);
  }

  findAll() {
    return this.http.get<Project[]>(`/projects/`);
  }

  update(p: Project) {
    return this.http.put(`/projects/${p.id}`, p);
  }

  addAbility(id: number, a: Ability) {
    return this.http.post(`/projects/${id}/abilities`, a);
  }

  removeAbility(id: number, idAbility: number) {
    return this.http.delete(`/projects/${id}/abilities/${idAbility}`);
  }

  addTechnology(id: number, t: Technology) {
    return this.http.post(`/projects/${id}/technologies`, t);
  }

  removeTechnology(id: number, idTechnology: number) {
    return this.http.delete(`/projects/${id}/technologies/${idTechnology}`);
  }

  delete(id: number) {
    return this.http.delete(`/projects/${id}`);
  }
}
