import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {AuthService} from '../../core/services/auth.service';
import {ProjectFormComponent} from '../project-form/project-form.component';
import {ProjectApiService} from "../../core/api/project-api.service";
import {Project} from "../../core/model/Project";

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
              private service: ProjectApiService,
              private auth: AuthService,
              public dialog: MatDialog) {
    this.logged = this.auth.isAuthenticated();
    this.project = data;
  }

  ngOnInit() {
  }

  async handleDelete(id: number) {
    await this.service.deleteProject(this.auth.getAuthString(), id);
    this.deleted = true;
    this.dialogRef.close(true);
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
