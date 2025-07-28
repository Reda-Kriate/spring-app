import { Component } from '@angular/core';
import {Avatar} from 'primeng/avatar';
import {InputText} from 'primeng/inputtext';
import {ButtonDirective} from 'primeng/button';
import {AuthenticationRequest} from '../../models/authentication-request';
import {FormsModule} from '@angular/forms';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {Message} from 'primeng/message';
import {NgIf} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    Avatar,
    InputText,
    ButtonDirective,
    FormsModule,
    Message,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})

export class LoginComponent {
  authenticationRequest : AuthenticationRequest = {}
  constructor(
    private authService : AuthenticationService,
    private router:Router
  ) {}
  errMsg?:string;
  login(){
    this.errMsg='';
    this.authService.login(this.authenticationRequest)
      .subscribe({
        next: (authResponse)=>{
          localStorage.setItem('user',JSON.stringify(authResponse));
          this.router.navigate(['customer'])
        },
        error:(err)=>{
          if(err.error.statusCode===401){
            this.errMsg='Login or / and password is incorrect';
          }
        }
      }
    );
  }
}
