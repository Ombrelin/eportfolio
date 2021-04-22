import {Component, Inject} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Technology} from "../../core/model/Technology";
import {AbilityApiService} from "../../core/api/ability-api.service";
import {TechnologyApiService} from "../../core/api/technology-api.service";
import {AuthService} from "../../core/services/auth.service";
import {FilesService} from "../../core/services/files.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-technology-form',
  templateUrl: './technology-form.component.html',
  styleUrls: ['./technology-form.component.css']
})
export class TechnologyFormComponent {

  public abilityId: number;
  public technology: Technology;
  private subjectId: number;
  public fileToUpload: File = null;

  constructor(
    private abilityService: AbilityApiService,
    private route: ActivatedRoute,
    private router: Router,
    private technologyService: TechnologyApiService,
    private dialogRef: MatDialogRef<TechnologyFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private authService: AuthService,
    private fileService: FilesService,
    private snackBar: MatSnackBar,
  ) {
    this.abilityId = data.abilityId;
    this.subjectId = data.subjectId;
    if (data.technology) {
      this.technology = data.technology;
    } else {
      this.technology = new Technology();
    }
  }

  async handleClickSave() {
    const authString = this.authService.getAuthString();
    if (this.fileToUpload) {
      this.technology.image = await this.fileService.upload(
        this.authService.getAuthString(),
        this.fileToUpload
      );
    }
    let response;
    if(this.technology.id == null) { // Create
      response =
        await this.technologyService.createTechnology(authString,this.technology,this.subjectId,this.abilityId)

      this.snackBar.open(`Technology created ${this.technology.id}`);
    } else { // Update
      response =
        await this.technologyService.updateTechnology(authString,this.technology,this.subjectId,this.abilityId, this.technology.id);

      this.snackBar.open(`Technology updated ${this.technology.id}`);
    }
    this.technology = response.data;
    this.dialogRef.close(response.data);
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }
}
