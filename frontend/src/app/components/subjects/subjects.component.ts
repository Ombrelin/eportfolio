import {Component, Inject, OnInit} from '@angular/core';
import {DOCUMENT} from '@angular/common';
import {AuthService} from '../../core/services/auth.service';
import {SubjectApiService} from "../../core/api/subject-api.service";
import {Subject} from "../../core/model/Subject";

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.css']
})
export class SubjectsComponent implements OnInit {

  public subjects: Array<Subject>;
  public logged: boolean;
  loaded: boolean;

  constructor(
    private service: SubjectApiService,
    @Inject(DOCUMENT) private document: Document,
    private auth: AuthService
  ) {
    this.refreshAuth();
    this.loaded = false;
  }

  ngOnInit() {
    this.loadSubjects();
  }

  refreshAuth() {
    this.logged = this.auth.isAuthenticated();
  }

  handleDelete(subject: Subject) {
    this.subjects = this.subjects.filter(e => e.id !== subject.id);
  }

  async loadSubjects() {
    const response = await this.service.getSubjects();
    this.subjects = response.data;
    this.loaded = true;
  }
}
