import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Subject} from '../../subject/subject.component';
import {Ability} from '../../ability/ability.component';
import {Project} from '../../project/project.component';

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  constructor(private http: HttpClient) {
  }

  findAll() {
    return this.http.get<Subject[]>(`/subjects/`);
  }

  create(s: Subject) {
    return this.http.post(`/subjects/`, s);
  }

  find(id: string) {
    return this.http.get<Subject>(`/subjects/${id}`);
  }

  update(s: Subject) {
    return this.http.put(`/subjects/${s.id}`, s);
  }

  delete(id: number) {
    return this.http.delete(`/subjects/${id}`);
  }

  addAbility(id: number, ability: Ability) {
    return this.http.post<Ability>(`/subjects/${id}/abilities`, ability);
  }

  addproject(id: number, project: Project) {
    return this.http.post<Project>(`/subjects/${id}/projects`, project);
  }

}


