import {Component, Input, OnInit} from '@angular/core';
import {DiplomasService} from '../../core/services/diplomas.service';
import {AuthService} from '../../core/services/auth.service';

@Component({
  selector: 'app-diplomas',
  templateUrl: './diplomas.component.html',
  styleUrls: ['./diplomas.component.css']
})
export class DiplomasComponent implements OnInit {

  public diplomas: Array<Diploma>;
  loaded: boolean;

  @Input() logged: boolean;

  constructor(private service: DiplomasService, private authService: AuthService) {
    this.loaded = false;
  }

  ngOnInit() {
    this.service.findAll().subscribe(result => {
      this.diplomas = result;
      this.loaded = true;
    });
  }

  delete(diploma: Diploma) {
    this.service.delete(diploma.id).subscribe(() => {
      for (const d of this.diplomas) {
        if (d.id === diploma.id) {
          this.diplomas.splice(this.diplomas.indexOf(diploma));
          break;
        }
      }
    });
  }
}

export class Diploma {
  constructor(
    public id?: number,
    public name?: string,
    public school?: string,
    public startYear?: number,
    public endYear?: number,
    public logo?: string,
    public description?: string
  ) {
  }
}
