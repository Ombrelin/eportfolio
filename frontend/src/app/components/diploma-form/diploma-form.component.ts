import {Component, OnInit} from '@angular/core';
import {Diploma} from '../diplomas/diplomas.component';
import {ActivatedRoute, Router} from '@angular/router';
import {DiplomasService} from '../../core/services/diplomas.service';

@Component({
  selector: 'app-diploma-form',
  templateUrl: './diploma-form.component.html',
  styleUrls: ['./diploma-form.component.css']
})
export class DiplomaFormComponent implements OnInit {

  private id: number;
  public diploma: Diploma;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: DiplomasService
  ) {
    this.diploma = new Diploma();
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    if (this.id !== null && this.id !== undefined) {
      this.service.find(this.id).subscribe(result => {
        this.diploma = result;
      });
    }
  }

  submit() {
    if (this.diploma.id == null) {
      this.service.create(this.diploma).subscribe(result => {
        this.router.navigate(['/']);
      });
    } else {
      this.service.update(this.diploma).subscribe(result => {

        this.router.navigate(['/']);
      });
    }
  }
}
