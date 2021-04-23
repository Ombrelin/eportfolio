import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from '../../core/services/auth.service';
import {DiplomaApiService} from "../../core/api/diploma-api.service";
import {Diploma} from "../../core/model/Diploma";
import {MatDialog} from "@angular/material/dialog";
import {DiplomaFormComponent} from "../diploma-form/diploma-form.component";

@Component({
  selector: 'app-diplomas',
  templateUrl: './diplomas.component.html',
  styleUrls: ['./diplomas.component.css']
})
export class DiplomasComponent implements OnInit {

  public diplomas: Array<Diploma>;
  loaded: boolean;

  @Input() logged: boolean;

  constructor(
    private service: DiplomaApiService,
    private authService: AuthService,
    private dialog: MatDialog) {
    this.loaded = false;
  }

  ngOnInit() {
    this.service.getAllDiplomas().then(result => {
      this.diplomas = result.data;
      this.loaded = true;
    });
  }

  async delete(diploma: Diploma) {
    await this.service.deleteDiploma(this.authService.getAuthString(), diploma.id);
    this.diplomas = this.diplomas.filter(d => d.id != diploma.id);
  }

  addDiploma(diploma: Diploma) {
    if (diploma) {
      this.diplomas.push(diploma);
    }
  }

  editDiploma(diploma: Diploma) {
    const dialogRef = this.dialog.open(DiplomaFormComponent, {
      width: '500px',
      data: diploma
    });
  }
}


