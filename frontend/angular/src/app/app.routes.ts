import { Routes } from '@angular/router';
import {CustomerComponent} from './components/customer/customer.component';
import {LoginComponent} from './components/login/login.component';



export const routes: Routes = [
  {path:"customer" , component:CustomerComponent},
  {path:"login" , component:LoginComponent}
];
