import {Injectable} from '@angular/core';
import {AuthApiService} from "../api/auth-api.service";
import {Auth} from "../model/Auth";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private authApi: AuthApiService) {
  }

  async authenticate(username: string, password: string) {
    const authResponse = await this.authApi.authenticate(new Auth(username, password));
    const bearerString = authResponse.headers.authorization;
    console.info(`Logged in as ${username}`)
    sessionStorage.setItem("token", bearerString);
  }

  getAuthString(): string {
    if (!this.isAuthenticated()) {
      throw new Error("Not autenticated");
    }

    return sessionStorage.getItem("token");
  }

  isAuthenticated(): boolean {
    return sessionStorage.getItem('token') !== undefined && sessionStorage.getItem('token') !== null;
  }
}


