import { Component } from '@angular/core';
import {MenuBarComponent} from '../menu-bar/menu-bar.component';
import {HeaderComponent} from '../header/header.component';
import {ButtonDirective} from 'primeng/button';
import {Ripple} from 'primeng/ripple';
import {Drawer} from 'primeng/drawer';
import {ManagementCustomerComponent} from '../management-customer/management-customer.component';

@Component({
  selector: 'app-customer',
  imports: [
    MenuBarComponent,
    HeaderComponent,
    ButtonDirective,
    Ripple,
    Drawer,
    ManagementCustomerComponent
  ],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.scss'
})
export class CustomerComponent {
  visible2: boolean = false;

}
