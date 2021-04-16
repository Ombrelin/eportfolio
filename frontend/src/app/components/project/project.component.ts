import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Subject} from '../subject/subject.component';
import {Ability, Technology} from '../ability/ability.component';
import {MatDialog} from '@angular/material/dialog';
import {ProjectModalComponent} from '../project-modal/project-modal.component';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  @Input() project: Project;
  @Input() logged: boolean;
  public trimedName: string;

  @Output() delete: EventEmitter<Project> = new EventEmitter<Project>();

  constructor(public dialog: MatDialog) {
  }

  ngOnInit() {
    this.trimedName = this.project.name.replace(/[^a-z]/gi, '');
  }

  showModale() {
    const dialogRef = this.dialog.open(ProjectModalComponent, {
      width: '500px',
      data: this.project
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.delete.emit(this.project);
      }
    });
  }
}

export class Project {
  constructor(
    public id?: number,
    public name?: string,
    public icon?: string,
    public description?: string,
    public git?: string,
    public color?: string,
    public subject?: Subject,
    public abilities?: Array<Ability>,
    public technologies?: Array<Technology>
  ) {
  }
}
