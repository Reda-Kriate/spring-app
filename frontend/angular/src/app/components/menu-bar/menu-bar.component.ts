import { Component } from '@angular/core';
import {Avatar} from 'primeng/avatar';
import {MenuItem} from 'primeng/api';
import {NgForOf} from '@angular/common';
import {MenuItemComponent} from '../menu-item/menu-item.component';


@Component({
  selector: 'app-menu-bar',
  imports: [
    Avatar,
    MenuItemComponent,
    NgForOf
  ],
  templateUrl: './menu-bar.component.html',
  styleUrl: './menu-bar.component.scss'
})
export class MenuBarComponent {
  menuItem : Array<MenuItem> = [
      {
        label: 'Home',
        icon: 'pi pi-home',
      },
    {
      label: 'Customers',
      icon: 'pi pi-users',
    },
      {
        label: 'Settings',
        icon: 'pi pi-cog',
      }
  ]
}
