import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {ProjectService} from '../service/data/project.service';
import {AuthService} from '../service/auth/auth.service';
import {Project} from '../project/project.component';
import {ProjectFormComponent} from '../project-form/project-form.component';

@Component({
  selector: 'app-project-modal',
  templateUrl: './project-modal.component.html',
  styleUrls: ['./project-modal.component.css']
})
export class ProjectModalComponent implements OnInit {

  public logged: boolean;
  public project: Project;
  public deleted = false;

  constructor(public dialogRef: MatDialogRef<ProjectModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Project,
              private service: ProjectService,
              private auth: AuthService,
              public dialog: MatDialog) {
    this.logged = this.auth.isAuthenticated();
    this.project = data;
  }

  ngOnInit() {
  }

  handleDelete(id: number) {
    this.service.delete(id).subscribe(() => {
      this.deleted = true;
      this.dialogRef.close(true);
    });
  }

  close() {
    this.dialogRef.close(false);
  }

  handleClickEdit() {
    const dialogRef = this.dialog.open(ProjectFormComponent, {
      width: '400px',
      data: this.project
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.project = result;
      }
    });
  }
}
