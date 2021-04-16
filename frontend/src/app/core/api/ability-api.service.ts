import { Injectable } from '@angular/core';
import {Ability, Technology} from '../../components/ability/ability.component';
import {BaseService, Header, GET, ServiceBuilder, Response, Path, PUT, POST, DELETE, Body} from "ts-retrofit";

@Injectable({
  providedIn: 'root'
})
export class AbilityApiService extends BaseService {

  @GET("/abilities")
  async getAbilities(@Header('Authorization') authorization: string): Promise<Response<Array<Ability>>> {
    return <Response<Array<Ability>>>{}
  }

  @GET("/subjects/{subjectId}/abilities/{id}")
  async getAbility(@Header('Authorization') authorization: string,
                   @Path("subjectId") subjectId: number,
                   @Path("id") id: number): Promise<Response<Ability>> {
    return <Response<Ability>>{}
  }

  @PUT("/subjects/{subjectId}/abilities/{abilityId}")
  async updateAbility(@Header('Authorization') authorization: string,
                      @Path("subjectId") subjectId: number,
                      @Path("id") id: number,
                      @Body ability: Ability) {
    return <Response<Ability>>{}
  }

  @POST("/subjects/{subjectId}/abilities")
  async createAbility(@Header('Authorization') authorization: string,
                      @Path("subjectId") subjectId: number,
                      @Body ability: Ability){
    return <Response<Ability>>{}
  }
  @DELETE("/subjects/{subjectId}/abilities/{abilityId}")
  async deleteAbility(@Header('Authorization') authorization: string,
                      @Path("subjectId") subjectId: number,
                      @Path("id") id: number){

  }
}

const createAbilityApi = (): AbilityApiService => new ServiceBuilder()
  .setEndpoint("")
  .setTimeout(4000)
  .build(AbilityApiService)

export {createAbilityApi}
