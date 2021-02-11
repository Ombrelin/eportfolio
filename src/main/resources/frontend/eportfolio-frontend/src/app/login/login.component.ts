import {Component, OnInit} from '@angular/core';
import {AuthService} from '../service/auth/auth.service';
import {MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public login: string;
  public password: string;
  public error: string;

  constructor(
    private auth: AuthService,
    public dialogRef: MatDialogRef<LoginComponent>,
    private snackBar: MatSnackBar
  ) {
    this.error = '';
    this.login = '';
    this.password = '';
  }

  ngOnInit() {
  }

  handleLogin() {
    this.auth.authenticate(this.login, this.password).subscribe(result => {

      this.dialogRef.close();
      this.snackBar.open('Connecté !');

      sessionStorage.setItem('token', result.headers.get('Authorization'));
      setTimeout(() => {
          this.snackBar.dismiss();
        },
        3000);

    });
  }

  cancel() {
    this.dialogRef.close();
  }
}
