import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../core/services/auth.service';
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

  async handleLogin() {
    await this.auth.authenticate(this.login, this.password);
    setTimeout(() => {
        this.snackBar.dismiss();
      },
      3000);
    this.cancel();
  }

  cancel() {
    this.dialogRef.close();
  }
}
