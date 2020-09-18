import {Component, ElementRef, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {DOCUMENT} from '@angular/common';
import {Subject} from '../subject/subject.component';
import {AbilityService} from '../service/data/ability.service';
import {AbilityModalComponent} from '../ability-modal/ability-modal.component';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-ability',
  templateUrl: './ability.component.html',
  styleUrls: ['./ability.component.css']
})
export class AbilityComponent implements OnInit {

  @Input() ability: Ability;

  @Input() subject: Subject;
  @Input() public logged: boolean;

  @Output() delete: EventEmitter<Ability> = new EventEmitter<Ability>();

  public trimedName: string;

  constructor(@Inject(DOCUMENT) private document: Document,
              private service: AbilityService,
              private elementRef: ElementRef,
              public dialog: MatDialog
  ) {

  }

  ngOnInit() {
    this.trimedName = this.ability.name.replace(/[^a-z]/gi, '');
  }

  showModale() {
    const dialogRef = this.dialog.open(AbilityModalComponent, {
      width: '500px',
      data: new AbilityModalData(this.ability, this.subject, this.logged)
    });

    dialogRef.afterClosed().subscribe(result => {

      if (result) {
        this.delete.emit(this.ability);
      }
    });
  }
}


export class Ability {
  constructor(
    public id?: number,
    public name?: string,
    public color?: string,
    public image?: string,
    public technologies?: Array<Technology>) {
  }
}

export class Technology {
  constructor(
    public id?: number,
    public name?: string,
    public image?: string
  ) {
  }
}

export class AbilityModalData {
  constructor(public ability: Ability,
              public subject: Subject,
              public logged: boolean) {

  }
}




