import {Component, Inject} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Technology} from "../../core/model/Technology";
import {AbilityApiService} from "../../core/api/ability-api.service";
import {TechnologyApiService} from "../../core/api/technology-api.service";
import {AuthService} from "../../core/services/auth.service";

@Component({
  selector: 'app-technology-form',
  templateUrl: './technology-form.component.html',
  styleUrls: ['./technology-form.component.css']
})
export class TechnologyFormComponent {

  public abilityId: number;
  public technology: Technology;
  private subjectId: number;

  constructor(
    private abilityService: AbilityApiService,
    private route: ActivatedRoute,
    private router: Router,
    private technologyService: TechnologyApiService,
    private dialogRef: MatDialogRef<TechnologyFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private authService: AuthService
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
    const response = this.technology.id == null ?
      await this.technologyService.createTechnology(authString,this.technology,this.subjectId,this.abilityId)
      :
      await this.technologyService.updateTechnology(authString,this.technology,this.subjectId,this.abilityId, this.technology.id);
    this.dialogRef.close(response.data);
  }
}
