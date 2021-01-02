import {Component, Inject} from '@angular/core';
import {Technology} from '../ability/ability.component';
import {AbilityService} from '../service/data/ability.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TechnologyService} from '../service/data/technology.service';
import { MatDialogRef } from '@angular/material/dialog';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-technology-form',
  templateUrl: './technology-form.component.html',
  styleUrls: ['./technology-form.component.css']
})
export class TechnologyFormComponent {

  public abilityId: number;
  public technology: Technology;

  constructor(
    private abilityService: AbilityService,
    private route: ActivatedRoute,
    private router: Router,
    private technologyService: TechnologyService,
    private dialogRef: MatDialogRef<TechnologyFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
    this.abilityId = data.abilityId;
    if (data.technology) {
      this.technology = data.technology;
    } else {
      this.technology = new Technology();
    }
  }

  handleClickSave() {
    if (this.technology.id == null) {
      this.abilityService.addTechnology(this.abilityId, this.technology).subscribe(result => {
        this.dialogRef.close(result);
      });
    } else {
      this.technologyService.update(this.technology).subscribe(result => {
        this.dialogRef.close(result);
      });
    }
  }
}
