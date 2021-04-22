import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AbilityApiService} from "../../core/api/ability-api.service";
import {Ability} from "../../core/model/Ability";
import {Technology} from "../../core/model/Technology";
import {ProjectApiService} from "../../core/api/project-api.service";
import {TechnologyApiService} from "../../core/api/technology-api.service";
import {CreateProjectDto} from "../../core/model/dtos/CreateProjectDto";
import {AuthService} from "../../core/services/auth.service";
import {UpdateProjectDto} from "../../core/model/dtos/UpdateProjectDto";
import {Project} from "../../core/model/Project";

@Component({
  selector: 'app-project-form',
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.css']
})
export class ProjectFormComponent implements OnInit {

  public project: Project;
  public abilities: Array<Ability>;
  public technologies: Array<Technology>;
  addedAbility: Ability;
  addedTechnology: Technology;

  constructor(
    private projectService: ProjectApiService,
    private abilityService: AbilityApiService,
    @Inject(MAT_DIALOG_DATA) public data: Project,
    private technologyService: TechnologyApiService,
    private snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<ProjectFormComponent>,
    private authService: AuthService
  ) {
    if (data) {
      this.project = data;
    } else {
      this.project = new Project();
    }
  }

  ngOnInit() {
    this.loadData();
  }

  async handleClickSave() {
    if (this.project.id) { // Update
      const dto = UpdateProjectDto.fromProject(this.project);
      const result = await this.projectService.updateProject(this.authService.getAuthString(), this.project.id, dto);
      this.dialogRef.close(result.data);
    } else { // Create
      const dto = CreateProjectDto.fromProject(this.project);
      const result = await this.projectService.createProject(this.authService.getAuthString(), dto);
      this.dialogRef.close(result.data);
    }
  }

  async removeAbility(abilityId: number) {
    if (this.project.id) {
      await this.projectService.removeAbility(this.authService.getAuthString(), this.project.id, abilityId);
    }
    this.project.abilities = this.project.abilities.filter(a => a.id !== abilityId);
  }

  async removeTechnology(technologyId: number) {
    if (this.project.id) {
      await this.projectService.removeTechnology(this.authService.getAuthString(), this.project.id, technologyId);
    }
    this.project.technologies = this.project.technologies.filter(t => t.id !== technologyId);
  }

  async addAbility() {
    if (this.project.id) {
      await this.projectService.addAbility(this.authService.getAuthString(), this.project.id, this.addedAbility.id);
    }
    this.project.abilities.push(this.addedAbility);
  }

  async addTechnology() {
    if (this.project.id) {
      await this.projectService.addTechnology(this.authService.getAuthString(), this.project.id, this.addedTechnology.id);
    }
    this.project.technologies.push(this.addedTechnology);
  }

  private async loadData() {
    this.abilities = (await this.abilityService.getAbilities()).data;
    this.technologies = (await this.technologyService.getTechnologies()).data;
  }
}
