import { Component } from '@angular/core';
import {Avatar} from 'primeng/avatar';
import {InputText} from 'primeng/inputtext';
import {ButtonDirective} from 'primeng/button';

@Component({
  selector: 'app-login',
  imports: [
    Avatar,
    InputText,
    ButtonDirective
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

}
