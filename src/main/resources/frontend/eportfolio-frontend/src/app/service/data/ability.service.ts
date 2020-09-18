import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Ability, Technology} from '../../ability/ability.component';

@Injectable({
  providedIn: 'root'
})
export class AbilityService {

  constructor(private http: HttpClient) {
  }

  find(id: number) {
    return this.http.get<Ability>(`/abilities/${id}`);
  }

  findall() {
    return this.http.get<Ability[]>(`/abilities/`);
  }

  update(ability: Ability) {
    return this.http.put(`/abilities/${ability.id}`, ability);
  }

  delete(id) {
    return this.http.delete(`/abilities/${id}`);
  }

  addTechnology(id: number, technology: Technology) {
    return this.http.post<Technology>(`https://eportfolio-arsene.herokuapp.com/abilities/${id}/technologies`, technology);
  }
}
