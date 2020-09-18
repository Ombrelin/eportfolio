import {Component, Input, OnInit} from '@angular/core';
import {ProjectService} from '../service/data/project.service';
import {Project} from '../project/project.component';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  public projects: Array<Project>;
  loaded: boolean;
  @Input() logged: boolean;

  constructor(private service: ProjectService) {
    this.loaded = false;
  }

  ngOnInit() {
    this.projects = new Array<Project>();
    this.service.findAll().subscribe(result => {
      this.projects = result;
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
