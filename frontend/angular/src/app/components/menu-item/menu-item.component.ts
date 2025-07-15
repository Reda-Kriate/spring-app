import {Component, Input} from '@angular/core';
import {NgForOf} from "@angular/common";
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-menu-item',
    imports: [
    ],
  templateUrl: './menu-item.component.html',
  styleUrl: './menu-item.component.scss'
})
export class MenuItemComponent {
  @Input()
  MenuitemCom:MenuItem = {};
}
