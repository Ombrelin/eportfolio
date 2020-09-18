import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Subject} from '../../subject/subject.component';
import {Ability} from '../../ability/ability.component';
import {Project} from '../../project/project.component';
import {EnvService} from "../env/env.service";

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  constructor(private http: HttpClient, private env: EnvService) {
  }

  findAll() {
    return this.http.get<Subject[]>(`${this.env.apiUrl}/subjects/`);
  }

  create(s: Subject) {
    return this.http.post(`${this.env.apiUrl}/subjects/`, s);
  }

  find(id: string) {
    return this.http.get<Subject>(`${this.env.apiUrl}/subjects/${id}`);
  }

  update(s: Subject) {
    return this.http.put(`${this.env.apiUrl}/subjects/${s.id}`, s);
  }

  delete(id: number) {
    return this.http.delete(`${this.env.apiUrl}/subjects/${id}`);
  }

  addAbility(id: number, ability: Ability) {
    return this.http.post<Ability>(`${this.env.apiUrl}/subjects/${id}/abilities`, ability);
  }

  addproject(id: number, project: Project) {
    return this.http.post<Project>(`${this.env.apiUrl}/subjects/${id}/projects`, project);
  }

}


