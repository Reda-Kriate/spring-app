import { Component } from '@angular/core';
import {InputText} from 'primeng/inputtext';
import {ButtonDirective} from 'primeng/button';

@Component({
  selector: 'app-management-customer',
  imports: [
    InputText,
    ButtonDirective
  ],
  templateUrl: './management-customer.component.html',
  styleUrl: './management-customer.component.scss'
})
export class ManagementCustomerComponent {

}
