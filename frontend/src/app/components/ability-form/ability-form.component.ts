import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FilesService} from '../../core/services/files.service';
import {Ability} from "../../core/model/Ability";
import {AbilityApiService} from "../../core/api/ability-api.service";
import {SubjectApiService} from "../../core/api/subject-api.service";
import {AuthService} from "../../core/services/auth.service";

@Component({
  selector: 'app-ability-form',
  templateUrl: './ability-form.component.html',
  styleUrls: ['./ability-form.component.css']
})
export class AbilityFormComponent implements OnInit {

  public ability: Ability;
  public fileToUpload: File = null;
  private readonly subjectId: number;
  private readonly abilityId: number;

  constructor(
    public dialogRef: MatDialogRef<AbilityFormComponent>,
    private subjectService: SubjectApiService,
    private abilityService: AbilityApiService,
    private fileService: FilesService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private snackBar: MatSnackBar,
    private authService: AuthService
  ) {
    if (this.data.ability) {
      this.ability = this.data.ability;
      this.abilityId = this.ability.id;
    } else {
      this.ability = new Ability();
    }
    this.subjectId = this.data.subjectId;
  }

  ngOnInit() {
  }

  handleClickSave() {
    this.save().then(result => this.dialogRef.close(result));
  }

  async save() {
    console.log(this.ability.color);
    // Create
    if (!this.ability.id) {
      if (this.fileToUpload) {
        this.ability.image = await this.fileService.upload(
          this.authService.getAuthString(),
          this.fileToUpload
        );
      }
      const response = await this.abilityService.createAbility(
        this.authService.getAuthString(),
        this.subjectId, this.ability
      );
      this.ability = response.data;
      this.snackBar.open(`Ability created ${this.ability.id}`);

    } else { // Update

      if (this.fileToUpload) {
        this.ability.image = await this.fileService.upload(
          this.authService.getAuthString(),
          this.fileToUpload
        );
      }
      const response = await this.abilityService.updateAbility(
        this.authService.getAuthString(),
        this.subjectId,
        this.abilityId,
        this.ability
      );
      this.ability = response.data;
      this.snackBar.open(`Ability updated ${this.ability.id}`);
    }

    setTimeout(() => {
        this.snackBar.dismiss();
      },
      3000);

    return this.ability;
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }
}
