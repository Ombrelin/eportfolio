import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AbilityApiService, createAbilityApi} from "./api/ability-api.service";
import {AuthApiService, createAuthApi} from "./api/auth-api.service";
import {createDiplomaApi, DiplomaApiService} from "./api/diploma-api.service";
import {createSubjectApi, SubjectApiService} from "./api/subject-api.service";
import {createTechnologyApi, TechnologyApiService} from "./api/technology-api.service";
import {createProjectApi, ProjectApiService} from "./api/project-api.service";


@NgModule({
    imports: [
      CommonModule
    ],
    providers:
      [
        {provide: AbilityApiService, useValue: createAbilityApi()},
        {provide: AuthApiService, useValue: createAuthApi()},
        {provide: DiplomaApiService, useValue: createDiplomaApi()},
        {provide: SubjectApiService, useValue: createSubjectApi()},
        {provide: ProjectApiService, useValue: createProjectApi()},
        {provide: TechnologyApiService, useValue: createTechnologyApi()}
      ]
  }
)
export class CoreModule {
}
