import {Component, Input} from '@angular/core';
import {Card} from 'primeng/card';
import {Button, ButtonDirective} from 'primeng/button';
import {Badge} from 'primeng/badge';
import {Ripple} from 'primeng/ripple';
import {authenticationResponse} from '../../models/authentication-response';
import {CustomerDTO} from '../../models/customer-dto';

@Component({
  selector: 'app-customer-card',
  imports: [
    Card,
    Badge,
    ButtonDirective,
  ],
  templateUrl: './customer-card.component.html',
  styleUrl: './customer-card.component.scss'
})
export class CustomerCardComponent {

@Input()
  customerDTO : CustomerDTO = {};
@Input()
  customerIndex : number=0;

}
