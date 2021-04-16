import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Ability} from '../ability/ability.component';
import {SubjectService} from '../../core/services/subject.service';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {AbilityFormComponent} from '../ability-form/ability-form.component';
import {SubjectFormComponent} from '../subject-form/subject-form.component';

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
    private service: SubjectService,
    private dialog: MatDialog
  ) {
  }

  ngOnInit() {

  }

  handleAddAbility() {
    this.dialogref = this.dialog.open(AbilityFormComponent, {
      width: '250px',
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

  handleDelete() {
    this.service.delete(this.subject.id).subscribe(() => {
      this.delete.emit(this.subject);
    });
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

export class Subject {
  constructor(
    public id?: number,
    public name?: string,
    public icon?: string,
    public image?: string,
    public abilities?: Array<Ability>) {
  }
}
