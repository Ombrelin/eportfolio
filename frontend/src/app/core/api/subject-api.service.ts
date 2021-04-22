import {BaseService, Body, DELETE, GET, Header, Path, POST, PUT, Response, ServiceBuilder} from "ts-retrofit";
import {API_URL} from "../constants/api-constants";
import {Subject} from "../model/Subject";

export class SubjectApiService extends BaseService {

  @GET("/subjects")
  async getSubjects(): Promise<Response<Array<Subject>>> {
    return <Response<Array<Subject>>> {};
  }

  @POST("/subjects")
  async createSubject(
    @Header('Authorization') authorization: string,
    @Body subject: Subject
  ): Promise<Response<Subject>> {
    return <Response<Subject>> {};
  }

  @PUT("/subjects/{id}")
  async updateSubject(
    @Header('Authorization') authorization: string,
    @Body subject: Subject,
    @Path("id") subjectId: number
  ): Promise<Response<Subject>> {
    return <Response<Subject>> {};
  }

  @DELETE("/subjects/{id}")
  async deleteSubject(
    @Header('Authorization') authorization: string,
    @Path("id") subjectId: number
  ): Promise<void> {
    return;
  }
}

const createSubjectApi = (): SubjectApiService => new ServiceBuilder()
  .setEndpoint(API_URL)
  .setTimeout(4000)
  .build(SubjectApiService);

export {createSubjectApi};

