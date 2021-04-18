import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Diploma} from "../../core/model/Diploma";
import {DiplomaApiService} from "../../core/api/diploma-api.service";
import {AuthService} from "../../core/services/auth.service";

@Component({
  selector: 'app-diploma-form',
  templateUrl: './diploma-form.component.html',
  styleUrls: ['./diploma-form.component.css']
})
export class DiplomaFormComponent implements OnInit {

  public diploma: Diploma;
  private id: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: DiplomaApiService,
    private authService: AuthService
  ) {
    this.diploma = new Diploma();
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    if (this.id !== null && this.id !== undefined) {
      this.service.getDiploma(this.id).then(result => {
        this.diploma = result.data;
      });
    }
  }

  async submit() {
    if (this.diploma.id == null) {
      await this.service.createDiploma(this.authService.getAuthString(), this.diploma);

    } else {
      await this.service.updateDiploma(this.authService.getAuthString(), this.diploma,this.diploma.id);
    }
    await this.router.navigate(['/']);
  }
}
