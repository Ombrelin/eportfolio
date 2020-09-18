import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Ability, Technology} from '../../ability/ability.component';
import {EnvService} from "../env/env.service";

@Injectable({
  providedIn: 'root'
})
export class AbilityService {

  constructor(private http: HttpClient, private env: EnvService) {
  }

  find(id: number) {
    return this.http.get<Ability>(`${this.env.apiUrl}/abilities/${id}`);
  }

  findall() {
    return this.http.get<Ability[]>(`${this.env.apiUrl}/abilities/`);
  }

  update(ability: Ability) {
    return this.http.put(`${this.env.apiUrl}/abilities/${ability.id}`, ability);
  }

  delete(id) {
    return this.http.delete(`${this.env.apiUrl}/abilities/${id}`);
  }

  addTechnology(id: number, technology: Technology) {
    return this.http.post<Technology>(`https://eportfolio-arsene.herokuapp.com/abilities/${id}/technologies`, technology);
  }
}
