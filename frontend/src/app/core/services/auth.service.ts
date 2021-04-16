import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {AuthApiService} from "../api/auth-api.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private authApi: AuthApiService) {
  }

  async authenticate(username: string, password: string) {
    const dto = {
      username,
      password
    };
    await this.authApi.authenticate(dto);
  }

  isAuthenticated(): boolean {
    return sessionStorage.getItem('token') != null;
  }
}


