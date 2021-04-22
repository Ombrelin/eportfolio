import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {ProjectsComponent} from './components/projects/projects.component';
import {LoginComponent} from './components/login/login.component';
import {SubjectFormComponent} from './components/subject-form/subject-form.component';
import {MainComponent} from './components/main/main.component';
import {ProjectComponent} from './components/project/project.component';
import {AbilityComponent} from './components/ability/ability.component';
import {SubjectsComponent} from './components/subjects/subjects.component';
import {SubjectComponent} from './components/subject/subject.component';
import {AbilityFormComponent} from './components/ability-form/ability-form.component';
import {TechnologyFormComponent} from './components/technology-form/technology-form.component';
import {ProjectFormComponent} from './components/project-form/project-form.component';
import {ExperiencesComponent} from './components/experiences/experiences.component';
import {AbilityModalComponent} from './components/ability-modal/ability-modal.component';
import {DiplomaFormComponent} from './components/diploma-form/diploma-form.component';
import {DiplomasComponent} from './components/diplomas/diplomas.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {MarkdownModule} from 'ngx-markdown';
import {ProjectModalComponent} from './components/project-modal/project-modal.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MccColorPickerModule} from 'material-community-components';
import {CoreModule} from "./core/core.module";

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
    MccColorPickerModule,
    CoreModule
  ],
  providers: [],
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
