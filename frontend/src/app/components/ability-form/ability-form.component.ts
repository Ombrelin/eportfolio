import {Component, Inject, OnInit} from '@angular/core';
import {Ability} from '../ability/ability.component';
import {SubjectService} from '../../core/services/subject.service';
// import {AbilityService} from '../../core/services/ability.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FilesService} from '../../core/services/files.service';

@Component({
  selector: 'app-ability-form',
  templateUrl: './ability-form.component.html',
  styleUrls: ['./ability-form.component.css']
})
export class AbilityFormComponent implements OnInit {

  private subjectId: number;
  private abilityId: number;
  public ability: Ability;
  public fileToUpload: File = null;

  constructor(
    public dialogRef: MatDialogRef<AbilityFormComponent>,
    private subjectService: SubjectService,
    // private abilityService: AbilityService,
    private fileService: FilesService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private snackBar: MatSnackBar
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
    if (this.ability.id === null || this.ability.id === undefined) {
      if (this.fileToUpload) {
        this.ability.image = await this.fileService.upload(this.fileToUpload);
      }
      this.ability = await this.subjectService.addAbility(this.subjectId, this.ability).toPromise();
      this.snackBar.open(`Ability created ${this.ability.id}`);

    } else { // Update

      if (this.fileToUpload) {
        this.ability.image = await this.fileService.upload(this.fileToUpload);
      }
      // this.ability = await this.abilityService.update(this.ability).toPromise();
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
