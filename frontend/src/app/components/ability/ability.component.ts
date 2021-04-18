import {Component, ElementRef, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {DOCUMENT} from '@angular/common';
import {AbilityModalComponent} from '../ability-modal/ability-modal.component';
import {MatDialog} from '@angular/material/dialog';
import {AbilityApiService} from "../../core/api/ability-api.service";
import {Ability} from "../../core/model/Ability";
import {Subject} from "../../core/model/Subject";


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
              private service: AbilityApiService,
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


export class AbilityModalData {
  constructor(public ability: Ability,
              public subject: Subject,
              public logged: boolean) {

  }
}




