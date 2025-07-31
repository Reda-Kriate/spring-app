import { Routes } from '@angular/router';
import {CustomerComponent} from './components/customer/customer.component';
import {LoginComponent} from './components/login/login.component';
import {GuardService} from './services/guard/guard.service';

export const routes: Routes = [
  {path:"" , redirectTo:"login" , pathMatch:"full"},
  {path:"customer" , component:CustomerComponent , canActivate:[GuardService]},
  {path:"login" , component:LoginComponent}
];
