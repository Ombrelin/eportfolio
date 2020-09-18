import {Component, Inject, OnInit} from '@angular/core';
import {Subject} from '../subject/subject.component';
import {SubjectService} from '../service/data/subject.service';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-subject-form',
  templateUrl: './subject-form.component.html',
  styleUrls: ['./subject-form.component.css']
})
export class SubjectFormComponent implements OnInit {

  public id: string;
  public subject: Subject;

  constructor(
    private service: SubjectService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<SubjectFormComponent>,
    private snackBar: MatSnackBar
  ) {
    if (this.data.subject) {
      this.subject = this.data.subject;
    } else {
      this.subject = new Subject();
    }
  }

  ngOnInit() {


  }

  submit() {
    if (this.subject.id == null) {
      this.service.create(this.subject).subscribe(result => {
        this.snackBar.open('Subject created');
      });
    } else {
      this.service.update(this.subject).subscribe(result => {
        this.snackBar.open('Subject updated');
      });
    }
    this.dialogRef.close();
    setTimeout(() => {
        this.snackBar.dismiss();
      },
      3000);

  }
}
