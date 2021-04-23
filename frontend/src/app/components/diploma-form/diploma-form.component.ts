import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Diploma} from "../../core/model/Diploma";
import {DiplomaApiService} from "../../core/api/diploma-api.service";
import {AuthService} from "../../core/services/auth.service";
import {FilesService} from "../../core/services/files.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-diploma-form',
  templateUrl: './diploma-form.component.html',
  styleUrls: ['./diploma-form.component.css']
})
export class DiplomaFormComponent implements OnInit {

  public diploma: Diploma;
  public fileToUpload: File = null;
  private id: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: DiplomaApiService,
    private authService: AuthService,
    private fileService: FilesService,
    private dialogRef: MatDialogRef<DiplomaFormComponent>,
    @Inject(MAT_DIALOG_DATA) private data: Diploma,
  ) {
    if (data) {
      this.diploma = data;
    } else {
      this.diploma = new Diploma();
    }

  }

  ngOnInit() {

  }

  async submit() {
    if (this.fileToUpload) {
      this.diploma.logo = await this.fileService.upload(
        this.authService.getAuthString(),
        this.fileToUpload
      );
    }

    const result = this.diploma.id == null ?
      await this.service.createDiploma(this.authService.getAuthString(), this.diploma)
      :
      await this.service.updateDiploma(this.authService.getAuthString(), this.diploma, this.diploma.id);

    this.dialogRef.close(result.data);
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }
}
