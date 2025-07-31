import {Component, Output} from '@angular/core';
import {Avatar} from 'primeng/avatar';
import {InputText} from 'primeng/inputtext';
import {ButtonDirective} from 'primeng/button';
import {AuthenticationRequest} from '../../models/authentication-request';
import {FormsModule} from '@angular/forms';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {Message} from 'primeng/message';
import {NgIf} from '@angular/common';
import {Router} from '@angular/router';
import {CustomerService} from '../../services/customer/customer.service';
import {customerRegistrationRequest} from '../../models/customerRegistrationRequest';
import {Toast} from 'primeng/toast';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-login',
  imports: [
    Avatar,
    InputText,
    ButtonDirective,
    FormsModule,
    Message,
    NgIf,
    Toast
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})

export class LoginComponent {

  authenticationRequest : AuthenticationRequest = {}
  errMsg?:string;
  visible = true
  customerReg : customerRegistrationRequest = {};
  errMsg2?:string;


  constructor(
    private authService : AuthenticationService,
    private router:Router,
    private customerService : CustomerService,
    private messageService: MessageService
  ) {}

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
  showSuccess() {
    this.messageService.add({ severity: 'success', summary: 'User added', detail: 'User added successfully' });
  }
  checkFields(input : customerRegistrationRequest){
    return input.name !== undefined && input.name !== null && input.name.length > 0 &&
      input.email !== undefined && input.email !== null && input.email.length > 0 &&
      input.password !== undefined && input.password !== null && input.password.length > 0 &&
      input.gender !== undefined && input.gender !== null && input.gender.length > 0 &&
      input.age !== undefined && input.age !== null && input.age > 0  ;
  }
  signUp() {
    if(this.checkFields(this.customerReg)) {
      this.customerService.saveCustomer(this.customerReg).subscribe({
        next:()=>{
            console.log(this.customerReg)
            this.showSuccess();
            this.visible=true;
        },
        error:()=>{
          console.log("errooooor")
          this.errMsg2 = 'There are items that require your attention';
        }
      })
    }else{
      this.errMsg2 = 'There are items that require your attention';
    }
  }
  switchInterface(input : boolean) {
    this.visible = input;
  }
}
