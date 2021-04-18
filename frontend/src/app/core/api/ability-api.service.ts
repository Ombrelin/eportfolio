import {BaseService, Body, DELETE, GET, Header, Path, POST, PUT, Response, ServiceBuilder} from "ts-retrofit";
import {Ability} from "../model/Ability";
import {API_URL} from "../constants/api-constants";

export class AbilityApiService extends BaseService {

  @GET("/abilities")
  async getAbilities(): Promise<Response<Array<Ability>>> {
    return <Response<Array<Ability>>> {};
  }

  @GET("/subjects/{subjectId}/abilities/{id}")
  async getAbility(@Path("subjectId") subjectId: number,
                   @Path("id") id: number): Promise<Response<Ability>> {
    return <Response<Ability>> {};
  }

  @PUT("/subjects/{subjectId}/abilities/{abilityId}")
  async updateAbility(@Header('Authorization') authorization: string,
                      @Path("subjectId") subjectId: number,
                      @Path("abilityId") id: number,
                      @Body ability: Ability) {
    return <Response<Ability>> {};
  }

  @POST("/subjects/{subjectId}/abilities")
  async createAbility(@Header('Authorization') authorization: string,
                      @Path("subjectId") subjectId: number,
                      @Body ability: Ability) {
    return <Response<Ability>> {};
  }

  @DELETE("/subjects/{subjectId}/abilities/{abilityId}")
  async deleteAbility(@Header('Authorization') authorization: string,
                      @Path("subjectId") subjectId: number,
                      @Path("abilityId") id: number) {

  }
}

const createAbilityApi = (): AbilityApiService => new ServiceBuilder()
  .setEndpoint(API_URL)
  .setTimeout(4000)
  .build(AbilityApiService);

export {createAbilityApi};
