import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {AbilityFormComponent} from '../ability-form/ability-form.component';
import {TechnologyFormComponent} from '../technology-form/technology-form.component';
import {AbilityApiService} from "../../core/api/ability-api.service";
import {Ability} from "../../core/model/Ability";
import {AbilityModalData} from "../ability/ability.component";
import {TechnologyApiService} from "../../core/api/technology-api.service";
import {AuthService} from "../../core/services/auth.service";
import {Technology} from "../../core/model/Technology";
import {Subject} from "../../core/model/Subject";

@Component({
  selector: 'app-ability-modal',
  templateUrl: './ability-modal.component.html',
  styleUrls: ['./ability-modal.component.css']
})
export class AbilityModalComponent implements OnInit {
  public subject: Subject;
  public ability: Ability;
  public logged: boolean;
  public deleted = false;

  constructor(
    public dialogRef: MatDialogRef<AbilityModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AbilityModalData,
    private service: AbilityApiService,
    private technologyService: TechnologyApiService,
    public dialog: MatDialog,
    private authService: AuthService
  ) {
    this.ability = data.ability;
    this.subject = data.subject;
    this.logged = data.logged;
  }

  ngOnInit() {

  }

  async handleDelete() {
    await this.service.deleteAbility(this.authService.getAuthString(), this.subject.id, this.ability.id);
    this.deleted = true;
    this.dialogRef.close(this.deleted);
  }

  async deleteTechnology(id: number) {
    await this.technologyService.deleteTechnology(this.authService.getAuthString(), this.subject.id, this.ability.id, id);
    this.ability.technologies = this.ability.technologies.filter(e => e.id !== id);
  }

  handleClickEditTechnology(technology: Technology) {
    const dialogRef = this.dialog.open(TechnologyFormComponent, {
      width: '400px',
      data: {
        abilityId: this.ability.id,
        subjectId: this.subject.id,
        technology
      }
    });

    dialogRef.afterClosed().subscribe((result: Technology) => {
      if(technology){
        technology = result;
      }
    });
  }

  handleClickAdd() {
    const dialogRef = this.dialog.open(TechnologyFormComponent, {
      width: '400px',
      data: {
        abilityId: this.ability.id,
        subjectId: this.subject.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.ability.technologies.push(result);
      }
    });
  }

  closeModale() {
    this.dialogRef.close(this.deleted);
  }

  handleClickEdit() {
    this.dialog.open(AbilityFormComponent, {
      width: '400px',
      data: {
        subjectId: this.subject.id,
        ability: this.ability
      }
    });
  }
}
