import { Component } from '@angular/core';
import {ButtonDirective} from 'primeng/button';
import {Ripple} from 'primeng/ripple';
import {Avatar} from 'primeng/avatar';
import {Menu} from 'primeng/menu';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-header',
  imports: [
    ButtonDirective,
    Ripple,
    Avatar,
    Menu
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  items: MenuItem[] = [
    {
      label: 'Profile',
      icon: 'pi pi-users'
    },
    {
      label: 'Settings',
      icon: 'pi pi-cog'
    },
    {separator:true},
    {
      label: 'Sign out',
      icon: 'pi pi-sign-out'
    }
]
}
