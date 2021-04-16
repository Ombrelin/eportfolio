import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {Subject} from '../subject/subject.component';
import {SubjectService} from '../../core/services/subject.service';
import {DOCUMENT} from '@angular/common';
import {AuthService} from '../../core/services/auth.service';
import {MatDialog} from '@angular/material/dialog';
import {LoginComponent} from '../login/login.component';
import {SubjectsComponent} from '../subjects/subjects.component';
import {SubjectFormComponent} from '../subject-form/subject-form.component';
import {ProjectFormComponent} from '../project-form/project-form.component';
import {ProjectsComponent} from '../projects/projects.component';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  public subjects: Array<Subject>;
  logged = false;
  createMenuOpen = false;

  subjectComponent: SubjectsComponent;

  @ViewChild(SubjectsComponent, {static: true})
  set subjectsComponent(subjects: SubjectsComponent) {
    this.subjectComponent = subjects;
  }

  projects: ProjectsComponent;

  @ViewChild(ProjectsComponent, {static: true})
  set projectsComponent(projects: ProjectsComponent) {
    this.projects = projects;
  }

  constructor(
    private service: SubjectService,
    @Inject(DOCUMENT) private document: Document,
    private auth: AuthService,
    public dialog: MatDialog
  ) {
    this.logged = auth.isAuthenticated();
  }

  ngOnInit() {

    this.subjects = new Array<Subject>();
    this.service.findAll().subscribe(response => {
      this.subjects = response;
    });
  }

  scrollTo(name: string) {
    this.document.querySelector(`#${name}`).scrollIntoView({behavior: 'smooth'});
  }

  toggleMenu() {
    this.createMenuOpen = !this.createMenuOpen;
  }

  showConnectDialog() {
    if (!this.logged) {
      const dialogRef = this.dialog.open(LoginComponent, {
        width: '250px'
      });

      dialogRef.afterClosed().subscribe(result => {
        this.logged = this.auth.isAuthenticated();
        this.subjectComponent.refreshAuth();
      });
    }
  }

  handleClickCreateSubject() {
    this.dialog.open(SubjectFormComponent, {
      width: '250px',
      data: {}
    });
  }

  handleClickCreateProject() {
    const dialogRef = this.dialog.open(ProjectFormComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.projects.addProject(result);
    });
  }

}
