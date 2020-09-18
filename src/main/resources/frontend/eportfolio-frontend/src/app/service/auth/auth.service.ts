import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {EnvService} from "../env/env.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private env: EnvService) {
  }

  authenticate(username: string, password: string) {
    const dto = {
      username,
      password
    };
    return this.http.post<HttpResponse<any>>(this.env.apiUrl, dto, {observe: 'response'});
  }

  isAuthenticated(): boolean {
    return sessionStorage.getItem('token') != null;
  }
}


