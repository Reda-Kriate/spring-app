import {Component, OnInit} from '@angular/core';
import {MenuBarComponent} from '../menu-bar/menu-bar.component';
import {HeaderComponent} from '../header/header.component';
import {ButtonDirective} from 'primeng/button';
import {Ripple} from 'primeng/ripple';
import {Drawer} from 'primeng/drawer';
import {ManagementCustomerComponent} from '../management-customer/management-customer.component';
import {CustomerService} from '../../services/customer/customer.service';
import {CustomerDTO} from '../../models/customer-dto';
import {NgForOf} from '@angular/common';
import {CustomerCardComponent} from '../customer-card/customer-card.component';

@Component({
  selector: 'app-customer',
  imports: [
    MenuBarComponent,
    HeaderComponent,
    ButtonDirective,
    Ripple,
    Drawer,
    ManagementCustomerComponent,
    CustomerCardComponent,
    NgForOf
  ],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.scss'
})
export class CustomerComponent implements OnInit{
  visible2: boolean = false;
  customers : Array<CustomerDTO> = [];
  constructor(
    private customerService : CustomerService
  ) {}

  ngOnInit(): void {
    this.findAll();
  }
  findAll(){
    this.customerService.findAllCustomers().subscribe({
        next:(data) => {
          this.customers = data;
          console.log(data);
        }
      }
    )
  }

}
