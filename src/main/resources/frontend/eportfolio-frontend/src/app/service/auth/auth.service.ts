import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  authenticate(username: string, password: string) {
    const dto = {
      username,
      password
    };
    return this.http.post<HttpResponse<any>>("/login", dto, {observe: 'response'});
  }

  isAuthenticated(): boolean {
    return sessionStorage.getItem('token') != null;
  }
}


