import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {ProjectsComponent} from './projects/projects.component';
import {LoginComponent} from './login/login.component';
import {SubjectFormComponent} from './subject-form/subject-form.component';
import {MainComponent} from './main/main.component';
import {ProjectComponent} from './project/project.component';
import {AbilityComponent} from './ability/ability.component';
import {SubjectsComponent} from './subjects/subjects.component';
import {SubjectComponent} from './subject/subject.component';
import {AbilityFormComponent} from './ability-form/ability-form.component';
import {TechnologyFormComponent} from './technology-form/technology-form.component';
import {ProjectFormComponent} from './project-form/project-form.component';
import {ExperiencesComponent} from './experiences/experiences.component';
import {AbilityModalComponent} from './ability-modal/ability-modal.component';
import {DiplomaFormComponent} from './diploma-form/diploma-form.component';
import {DiplomasComponent} from './diplomas/diplomas.component';
import {AuthInterceptorService} from './service/http/auth-interceptor.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {MarkdownModule} from 'ngx-markdown';
import {ProjectModalComponent} from './project-modal/project-modal.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MccColorPickerModule} from 'material-community-components';

@NgModule({
  declarations: [
    AppComponent,
    SubjectComponent,
    SubjectsComponent,
    AbilityComponent,
    ProjectsComponent,
    ProjectComponent,
    SubjectFormComponent,
    LoginComponent,
    MainComponent,
    AbilityFormComponent,
    TechnologyFormComponent,
    ProjectFormComponent,
    DiplomasComponent,
    ExperiencesComponent,
    DiplomaFormComponent,
    AbilityModalComponent,
    ProjectModalComponent
  ],
  imports: [
    MatSnackBarModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    AppRoutingModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    MarkdownModule.forRoot(),
    MccColorPickerModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}],
  bootstrap: [AppComponent],

  entryComponents: [AbilityModalComponent,
    ProjectModalComponent,
    LoginComponent,
    AbilityFormComponent,
    SubjectFormComponent,
    TechnologyFormComponent
  ]
})
export class AppModule {
}
