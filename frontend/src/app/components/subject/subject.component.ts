import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {AbilityFormComponent} from '../ability-form/ability-form.component';
import {SubjectFormComponent} from '../subject-form/subject-form.component';
import {Ability} from "../../core/model/Ability";
import {SubjectApiService} from "../../core/api/subject-api.service";
import {AuthService} from "../../core/services/auth.service";
import {Subject} from "../../core/model/Subject";

@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.css']
})
export class SubjectComponent implements OnInit {
  // New new subject

  @Input() subject: Subject;
  @Input() logged: boolean;

  @Output() delete: EventEmitter<Subject> = new EventEmitter<Subject>();

  dialogref: MatDialogRef<AbilityFormComponent, Ability>;

  constructor(
    private service: SubjectApiService,
    private dialog: MatDialog,
    private authService: AuthService
  ) {
  }

  ngOnInit() {

  }

  handleAddAbility() {
    this.dialogref = this.dialog.open(AbilityFormComponent, {
      width: '400px',
      data: {
        subjectId: this.subject.id
      }
    });

    this.dialogref.afterClosed().subscribe(result => {
      if (result && this.subject.abilities.filter(e => e.id === result.id).length === 0) {
        this.subject.abilities.push(result);
      }

    });
  }

  async handleDelete() {
    await this.service.deleteSubject(this.authService.getAuthString(), this.subject.id);
    this.delete.emit(this.subject);
  }


  handleUpdateSubject() {
    this.dialog.open(SubjectFormComponent, {
      width: '250px',
      data: {
        subject: this.subject
      }
    });
  }

  handleDeleteAbility(ability: Ability) {
    this.subject.abilities = this.subject.abilities.filter(e => ability.id !== e.id);
  }
}

