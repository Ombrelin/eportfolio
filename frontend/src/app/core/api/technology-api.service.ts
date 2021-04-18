import {BaseService, Body, DELETE, GET, Header, Path, POST, PUT, Response, ServiceBuilder} from "ts-retrofit";
import {Technology} from "../model/Technology";
import {SubjectApiService} from "./subject-api.service";
import {API_URL} from "../constants/api-constants";

export class TechnologyApiService extends BaseService {

  @PUT("/subjects/{subjectId}/abilities/{abilityId}/technologies/{techId}")
  async updateTechnology(
    @Header('Authorization') authorization: string,
    @Body technology: Technology,
    @Path("subjectId") subjectId: number,
    @Path("abilityId") abilityId: number,
    @Path("techId") techId: number
  ): Promise<Response<Technology>> {
    return <Response<Technology>> {};
  }

  @POST("/subjects/{subjectId}/abilities/{abilityId}/technologies")
  async createTechnology(
    @Header('Authorization') authorization: string,
    @Body technology: Technology,
    @Path("subjectId") subjectId: number,
    @Path("abilityId") abilityId: number
  ): Promise<Response<Technology>> {
    return <Response<Technology>> {};
  }

  @DELETE("/subjects/{subjectId}/abilities/{abilityId}/technologies/{techId}")
  async deleteTechnology(
    @Header('Authorization') authorization: string,
    @Path("subjectId") subjectId: number,
    @Path("abilityId") abilityId: number,
    @Path("techId") techId: number
  ): Promise<void> {
    return;
  }

  @GET("/technologies")
  async getTechnologies(): Promise<Response<Array<Technology>>> {
    return <Response<Array<Technology>>> {};
  }
}

const createTechnologyApi = (): TechnologyApiService => new ServiceBuilder()
  .setEndpoint(API_URL)
  .setTimeout(4000)
  .build(TechnologyApiService);

export {createTechnologyApi};

