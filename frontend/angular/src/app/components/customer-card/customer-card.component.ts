import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Card} from 'primeng/card';
import {ButtonDirective} from 'primeng/button';
import {Badge} from 'primeng/badge';
import {CustomerDTO} from '../../models/customer-dto';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import {CustomerUpdate} from '../../models/customerUpdate';


@Component({
  selector: 'app-customer-card',
  imports: [
    Card,
    Badge,
    ButtonDirective,
    ConfirmDialogModule
  ],
  templateUrl: './customer-card.component.html',
  styleUrl: './customer-card.component.scss'
})
export class CustomerCardComponent {
constructor() {
}
@Input()
  customerDTO : CustomerDTO = {};
@Input()
  customerIndex : number=0;
@Output()
  delete:EventEmitter<CustomerDTO>=new EventEmitter<CustomerDTO>();
@Output()
  update:EventEmitter<CustomerDTO>=new EventEmitter<CustomerDTO>();
customerUp : CustomerUpdate = {};
get customerImage():string{
  const gender = this.customerDTO.gender === 'MALE' ? 'men' : 'women';
  return `https://randomuser.me/api/portraits/${gender}/${this.customerIndex}.jpg`;
}
  deleteButton() {
  this.delete.emit(this.customerDTO);
  }
  updateButton() {
    this.update.emit(this.customerDTO);
  }
}
