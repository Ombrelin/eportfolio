import {BaseService, Body, POST, Response, ServiceBuilder} from "ts-retrofit";
import {Auth} from "../model/Auth";
import {API_URL} from "../constants/api-constants";


export class AuthApiService extends BaseService {

  @POST("/login")
  async authenticate(@Body auth: Auth): Promise<Response> {
    return <Response> {};
  }

}

const createAuthApi = (): AuthApiService => new ServiceBuilder()
  .setEndpoint(API_URL)
  .setTimeout(4000)
  .build(AuthApiService);

export {createAuthApi};
