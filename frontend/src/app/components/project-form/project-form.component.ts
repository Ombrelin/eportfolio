import {Component, Inject, OnInit} from '@angular/core';
import {Project} from '../project/project.component';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AbilityApiService} from "../../core/api/ability-api.service";
import {Ability} from "../../core/model/Ability";
import {Technology} from "../../core/model/Technology";
import {SubjectApiService} from "../../core/api/subject-api.service";
import {ProjectApiService} from "../../core/api/project-api.service";
import {TechnologyApiService} from "../../core/api/technology-api.service";
import {Subject} from "../../core/model/Subject";

@Component({
  selector: 'app-project-form',
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.css']
})
export class ProjectFormComponent implements OnInit {

  public project: Project;
  public subjects: Array<Subject>;
  public addedAbility: Ability;
  public addedTechnology: Technology;
  public abilities: Array<Ability>;
  public technologies: Array<Technology>;

  constructor(
    private subjectService: SubjectApiService,
    private projectService: ProjectApiService,
    private abilityService: AbilityApiService,
    @Inject(MAT_DIALOG_DATA) public data: Project,
    private technologyService: TechnologyApiService,
    private snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<ProjectFormComponent>
  ) {
    if (data) {
      this.project = data;
    } else {
      this.project = new Project();
    }
    this.addedTechnology = new Technology();
    this.addedAbility = new Ability();

  }

  ngOnInit() {
    this.abilityService.getAbilities().then(result => {
      this.abilities = result.data;
    });

    this.technologyService.getTechnologies().then(result => {
      this.technologies = result.data;
    });

    this.subjectService.getSubjects().then(result => {
      this.subjects = result.data;
    });
  }

  handleClickSave() {
    // if (this.project.subject === null || this.project.subject === undefined) {
    //   this.snackBar.open('Please set a subject');
    //   setTimeout(() => {
    //     this.snackBar.dismiss();
    //   }, 2000);
    // } else {
    //   if (this.project.id == null) {
    //     this.subjectService.addproject(this.project.subject.id, this.project).subscribe(result => {
    //       this.dialogRef.close(result);
    //     });
    //   } else {
    //     this.projectService.update(this.project).subscribe(result => {
    //       this.dialogRef.close(result);
    //     });
    //   }
    // }

  }

  removeAbility(projectId: number, abilityId: number) {
    // this.projectService.removeAbility(projectId, abilityId).subscribe(result => {
    //   for (const a of this.project.abilities) {
    //     if (a.id === abilityId) {
    //       this.project.abilities.splice(this.project.abilities.indexOf(a));
    //     }
    //   }
    // });
  }

  removeTechnology(projectId: number, technologyId: number) {
    // this.projectService.removeTechnology(projectId, technologyId).subscribe(result => {
    //   for (const t of this.project.technologies) {
    //     if (t.id === technologyId) {
    //       this.project.technologies.splice(this.project.technologies.indexOf(t));
    //     }
    //   }
    // });
  }

  addAbility() {
    // this.projectService.addAbility(this.project.id, this.addedAbility).subscribe(result => {
    //   if (!this.project.abilities) {
    //     this.project.abilities = new Array<Ability>();
    //   }
    //   this.project.abilities.push(this.addedAbility);
    // });
  }

  addTechnology() {
    // this.projectService.addTechnology(this.project.id, this.addedTechnology).subscribe(result => {
    //   if (!this.project.technologies) {
    //     this.project.technologies = new Array<Technology>();
    //   }
    //   this.project.technologies.push(this.addedTechnology);
    // });
  }
}
