import {Component, EventEmitter, Input, Output} from '@angular/core';
import {InputText} from 'primeng/inputtext';
import {ButtonDirective} from 'primeng/button';
import {customerRegistrationRequest} from '../../models/customerRegistrationRequest';
import {FormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-management-customer',
  imports: [
    InputText,
    ButtonDirective,
    FormsModule
  ],
  templateUrl: './management-customer.component.html',
  styleUrl: './management-customer.component.scss'
})
export class ManagementCustomerComponent {
  @Input()
  customerRegistration : customerRegistrationRequest = {};
  @Output()
  submit:EventEmitter<customerRegistrationRequest> = new EventEmitter<customerRegistrationRequest>();

  get isCustomerValid():boolean{
    return this.isAttributesValid(this.customerRegistration.name) &&
      this.isAttributesValid(this.customerRegistration.email) &&
      this.isAttributesValid(this.customerRegistration.password) &&
      this.isAttributesValid(this.customerRegistration.gender) &&
      this.customerRegistration.age !== undefined && this.customerRegistration.age > 0;
}

private isAttributesValid(input : string | undefined):boolean{
    return input !== undefined && input !== null && input.length > 0;
}

  onSubmit() {
    return this.submit.emit(this.customerRegistration);
  }
}
