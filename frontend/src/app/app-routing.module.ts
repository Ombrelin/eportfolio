import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {MainComponent} from './components/main/main.component';
import {SubjectFormComponent} from './components/subject-form/subject-form.component';
import {AbilityFormComponent} from './components/ability-form/ability-form.component';
import {TechnologyFormComponent} from './components/technology-form/technology-form.component';
import {ProjectFormComponent} from './components/project-form/project-form.component';
import {DiplomaFormComponent} from './components/diploma-form/diploma-form.component';


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '', component: MainComponent},
  {path: 'subject/:id', component: SubjectFormComponent},
  {path: 'subject', component: SubjectFormComponent},
  {path: 'subject/:subjectId/ability', component: AbilityFormComponent},
  {path: 'subject/:subjectId/ability/:abilityId', component: AbilityFormComponent},
  {path: 'ability/:abilityId/technology', component: TechnologyFormComponent},
  {path: 'ability/:abilityId/technology/:technologyId', component: TechnologyFormComponent},
  {path: 'project', component: ProjectFormComponent},
  {path: 'project/:id', component: ProjectFormComponent},
  {path: 'diploma', component: DiplomaFormComponent},
  {path: 'diploma/:id', component: DiplomaFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
