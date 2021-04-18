import {BaseService, Body, DELETE, GET, Header, Path, POST, PUT, Response, ServiceBuilder} from "ts-retrofit";
import {API_URL} from "../constants/api-constants";
import {Diploma} from "../model/Diploma";

export class DiplomaApiService extends BaseService {

  @POST("/diplomas")
  async createDiploma(@Header('Authorization') authorization: string,
                      @Body d: Diploma): Promise<Response<Diploma>> {
    return <Response<Diploma>> {};
  }

  @GET("/diplomas")
  async getAllDiplomas(): Promise<Response<Array<Diploma>>> {
    return <Response<Array<Diploma>>> {};
  }

  @GET("/diplomas/{id}")
  async getDiploma(@Path("id") id: number): Promise<Response<Diploma>> {
    return <Response<Diploma>> {};
  }

  @PUT("/diplomas/{id}")
  async updateDiploma(@Header('Authorization') authorization: string,
                      @Body d: Diploma, @Path("id") id: number): Promise<Response<Diploma>> {
    return <Response<Diploma>> {};
  }

  @DELETE("/diplomas/{id}")
  async deleteDiploma(@Header('Authorization') authorization: string, @Path("id") id: number): Promise<void> {
    return;
  }
}

const createDiplomaApi = (): DiplomaApiService => new ServiceBuilder()
  .setEndpoint(API_URL)
  .setTimeout(4000)
  .build(DiplomaApiService);

export {createDiplomaApi};
