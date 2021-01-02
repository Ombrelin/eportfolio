import {Component, Inject, OnInit} from '@angular/core';
import {Subject} from '../subject/subject.component';
import {SubjectService} from '../service/data/subject.service';
import {DOCUMENT} from '@angular/common';
import {AuthService} from '../service/auth/auth.service';

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
    private service: SubjectService,
    @Inject(DOCUMENT) private document: Document,
    private auth: AuthService
  ) {
    this.refreshAuth();
    this.loaded = false;
  }

  ngOnInit() {

    this.subjects = new Array<Subject>();
    this.service.findAll().subscribe(response => {
      this.subjects = response;
      this.loaded = true;
    });
  }

  refreshAuth() {
    this.logged = this.auth.isAuthenticated();
  }

  handleDelete(subject: Subject) {
    this.subjects = this.subjects.filter(e => e.id !== subject.id);
  }
}
