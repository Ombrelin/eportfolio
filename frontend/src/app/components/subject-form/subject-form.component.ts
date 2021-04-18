import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {SubjectApiService} from "../../core/api/subject-api.service";
import {AuthService} from "../../core/services/auth.service";
import {Subject} from "../../core/model/Subject";

@Component({
  selector: 'app-subject-form',
  templateUrl: './subject-form.component.html',
  styleUrls: ['./subject-form.component.css']
})
export class SubjectFormComponent implements OnInit {

  public id: string;
  public subject: Subject;

  constructor(
    private service: SubjectApiService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<SubjectFormComponent>,
    private snackBar: MatSnackBar,
    private authService: AuthService
  ) {
    if (this.data.subject) {
      this.subject = this.data.subject;
    } else {
      this.subject = new Subject();
    }
  }

  ngOnInit() {


  }

  async submit() {
    if (this.subject.id == null) {
      await this.service.createSubject(this.authService.getAuthString(), this.subject);
      this.snackBar.open('Subject created');
    } else {
      await this.service.updateSubject(this.authService.getAuthString(), this.subject, this.subject.id);
      this.snackBar.open('Subject updated');
    }
    this.dialogRef.close();
    setTimeout(() => {
      this.snackBar.dismiss();
    }, 3000);

  }
}
