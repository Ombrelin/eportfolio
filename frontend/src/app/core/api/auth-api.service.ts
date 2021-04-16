import { Injectable } from '@angular/core';
import {BaseService, Body, Header, Path, POST, Response, ServiceBuilder} from "ts-retrofit";
import {Ability} from "../../components/ability/ability.component";
import {Auth} from "../model/Auth";

@Injectable({
  providedIn: 'root'
})
export class AuthApiService extends BaseService{

  @POST("/login")
  async authenticate(@Body auth: Auth){
    return <Response>{}
  }

}

const createAuthApi = (): AuthApiService => new ServiceBuilder()
  .setEndpoint("")
  .setTimeout(4000)
  .build(AuthApiService)

export {createAuthApi}
