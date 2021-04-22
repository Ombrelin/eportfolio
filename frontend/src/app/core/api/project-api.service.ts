
import {BaseService, Body, DELETE, GET, Header, Path, POST, PUT, Response, ServiceBuilder} from "ts-retrofit";
import {API_URL} from "../constants/api-constants";
import {CreateProjectDto} from "../model/dtos/CreateProjectDto";
import {Project} from "../model/Project";
import {UpdateProjectDto} from "../model/dtos/UpdateProjectDto";

export class ProjectApiService extends BaseService {


  @PUT("/projects/{id}")
  async updateProject(@Header('Authorization') authorization: string,
                      @Path("id") id: number,
                      @Body project: UpdateProjectDto
  ): Promise<Response<Project>> {
    return <Response<Project>> {};
  }

  @DELETE("/projects/{id}")
  async deleteProject(@Header('Authorization') authorization: string,
                      @Path("id") id: number): Promise<void> {
    return;
  }

  @PUT("/projects/{id}/technologies/{idTech}")
  async addTechnology(@Header('Authorization') authorization: string,
                      @Path("id") id: number,
                      @Path("idTech") idTech: number): Promise<Response<Project>> {
    return <Response<Project>> {};
  }

  @DELETE("/projects/{id}/technologies/{idTech}")
  async removeTechnology(@Header('Authorization') authorization: string,
                         @Path("id") id: number,
                         @Path("idTech") idTech: number): Promise<void> {
    return;
  }

  @PUT("/projects/{id}/abilities/{abilityId}")
  async addAbility(@Header('Authorization') authorization: string,
                   @Path("id") id: number,
                   @Path("abilityId") idTech: number): Promise<Response<Project>> {
    return <Response<Project>> {};
  }

  @GET("/projects")
  async getProjects(): Promise<Response<Array<Project>>> {
    return <Response<Array<Project>>> {};
  }

  @POST("/projects")
  async createProject(@Header('Authorization') authorization: string,
                      @Body project: CreateProjectDto
  ): Promise<Response<Project>> {
    return <Response<Project>> {};
  }

  @DELETE("/projects/{id}/abilities/{idAbility}")
  async removeAbility(@Header('Authorization') authorization: string,
                      @Path("id") id: number,
                      @Path("idAbility") idAbility: number
  ): Promise<void> {
    return;
  }
}

const createProjectApi = (): ProjectApiService => new ServiceBuilder()
  .setEndpoint(API_URL)
  .setTimeout(4000)
  .build(ProjectApiService);

export {createProjectApi};
