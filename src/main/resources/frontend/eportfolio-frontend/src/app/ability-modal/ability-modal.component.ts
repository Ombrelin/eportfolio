import {Component, Inject, OnInit} from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import {Ability, AbilityModalData, Technology} from '../ability/ability.component';
import {Subject} from '../subject/subject.component';
import {AbilityService} from '../service/data/ability.service';
import {TechnologyService} from '../service/data/technology.service';
import {AbilityFormComponent} from '../ability-form/ability-form.component';
import {TechnologyFormComponent} from '../technology-form/technology-form.component';

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
    private service: AbilityService,
    private technologyService: TechnologyService,
    public dialog: MatDialog
  ) {
    this.ability = data.ability;
    this.subject = data.subject;
    this.logged = data.logged;
  }

  ngOnInit() {

  }

  handleDelete() {
    this.service.delete(this.ability.id).subscribe(() => {
        this.deleted = true;
        this.dialogRef.close(this.deleted);
      }
    );
  }

  deleteTechnology(id: number) {
    this.technologyService.delete(id).subscribe(() => {
      this.ability.technologies = this.ability.technologies.filter(e => e.id !== id);
    });
  }

  handleClickEditTechnology(technology: Technology) {
    const dialogRef = this.dialog.open(TechnologyFormComponent, {
      width: '250px',
      data: {
        abilityId: this.subject.id,
        technology
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      technology = result;
    });
  }

  handleClickAdd() {
    const dialogRef = this.dialog.open(TechnologyFormComponent, {
      width: '250px',
      data: {
        abilityId: this.ability.id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ability.technologies.push(result);
    });
  }

  closeModale() {
    this.dialogRef.close(this.deleted);
  }

  handleClickEdit() {
    const dialogRef = this.dialog.open(AbilityFormComponent, {
      width: '250px',
      data: {
        subjectId: this.subject.id,
        ability: this.ability
      }
    });
  }
}
