import {Component, OnInit, Output} from '@angular/core';
import {MenuBarComponent} from '../menu-bar/menu-bar.component';
import {HeaderComponent} from '../header/header.component';
import {Button, ButtonDirective} from 'primeng/button';
import {Ripple} from 'primeng/ripple';
import {Drawer} from 'primeng/drawer';
import {ManagementCustomerComponent} from '../management-customer/management-customer.component';
import {CustomerService} from '../../services/customer/customer.service';
import {CustomerDTO} from '../../models/customer-dto';
import {CustomerCardComponent} from '../customer-card/customer-card.component';
import {customerRegistrationRequest} from '../../models/customerRegistrationRequest';
import {NgForOf} from '@angular/common';
import {ConfirmationService, MessageService} from 'primeng/api';
import {Toast} from 'primeng/toast';
import {ConfirmDialog} from 'primeng/confirmdialog';
import {Avatar} from 'primeng/avatar';
import {Dialog} from 'primeng/dialog';
import {InputText} from 'primeng/inputtext';
import {FormsModule} from '@angular/forms';
import {CustomerUpdate} from '../../models/customerUpdate';

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
    Toast,
    ConfirmDialog,
    Button,
    Avatar,
    Dialog,
    InputText,
    FormsModule
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
    private messageService : MessageService,
    private confirmationService : ConfirmationService
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
  delete(customer:CustomerDTO){
    this.confirmationService.confirm({
      message: `Do you want to delete customer with name ${customer.name}`,
      header: 'Delete customer',


      accept: () => {
        this.customerService.deleteCustomer(customer.id).subscribe({
          next:()=>{
            this.findAll();
            this.messageService.add({ severity: 'success', summary: 'Customer deleted', detail: `Customer ${customer.name} deleted successfully` });
          }
        })

      },
    });
  }
  visible: boolean = false;
  customerUpdate : CustomerUpdate = {}
  id : number | undefined = 0;
  update(customer: CustomerDTO) {
      this.visible = true;
      this.customerUpdate.name = customer.name;
      this.customerUpdate.email = customer.email;
      this.customerUpdate.age = customer.age;
      this.customerUpdate.gender = customer.gender;
      this.id = customer.id;
      console.log(this.customerUpdate);
      console.log(this.id);
  }

  saveUpdates() {
    this.customerService.updateCustomer(this.id,this.customerUpdate).subscribe({
      next: ()=>{
        console.log("update success")
        this.findAll()
      }
    })
  }

  showToast() {
      this.messageService.add({ severity: 'success', summary: 'Update success', detail: `Update customer successfully` });
  }
}
