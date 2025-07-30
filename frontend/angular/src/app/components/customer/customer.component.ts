import {Component, OnInit, Output} from '@angular/core';
import {MenuBarComponent} from '../menu-bar/menu-bar.component';
import {HeaderComponent} from '../header/header.component';
import {ButtonDirective} from 'primeng/button';
import {Ripple} from 'primeng/ripple';
import {Drawer} from 'primeng/drawer';
import {ManagementCustomerComponent} from '../management-customer/management-customer.component';
import {CustomerService} from '../../services/customer/customer.service';
import {CustomerDTO} from '../../models/customer-dto';
import {CustomerCardComponent} from '../customer-card/customer-card.component';
import {customerRegistrationRequest} from '../../models/customerRegistrationRequest';
import {NgForOf} from '@angular/common';
import {MessageService} from 'primeng/api';
import {Toast} from 'primeng/toast';

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
    NgForOf,
    Toast
  ],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.scss'
})
export class CustomerComponent implements OnInit{
  visible2: boolean = false;
  customers : Array<CustomerDTO> = [];
  customer:customerRegistrationRequest = {};
  constructor(
    private customerService : CustomerService,
    private messageService : MessageService
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
  save(customer: customerRegistrationRequest){
    if(customer){
      this.customerService.saveCustomer(customer).subscribe({
        next:() =>{
          this.findAll();
          this.visible2=false;
          this.customer={};
          this.messageService.add({ severity: 'success',
            summary: 'Customer saved',
            detail: `Customer \`${customer.name}\` successfully saved` });
        }
      })
    }
  }
}
