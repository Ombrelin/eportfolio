import {Component, Input, OnInit} from '@angular/core';
//import {ProjectService} from '../../core/services/project.service';
import {ProjectApiService} from "../../core/api/project-api.service";
import {Project} from "../../core/model/Project";

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  public projects: Array<Project>;
  loaded: boolean = false;
  @Input() logged: boolean;

  constructor(private service: ProjectApiService) {
  }

  ngOnInit() {
    this.projects = new Array<Project>();
    this.service.getProjects().then(result => {
      this.projects = result.data;
      this.loaded = true;
    });
  }

  addProject(project: Project) {
    if (project) {
      this.projects.push(project);
    }
  }

  handleDeleteProject(project: Project) {
    this.projects = this.projects.filter(e => e.id !== project.id);
  }
}
