import {Component, OnInit} from '@angular/core';
import {ButtonDirective} from 'primeng/button';
import {Ripple} from 'primeng/ripple';
import {Avatar} from 'primeng/avatar';
import {Menu} from 'primeng/menu';
import {MenuItem} from 'primeng/api';
import {Router} from '@angular/router';

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
export class HeaderComponent implements OnInit{
  constructor(
    private router : Router
  ) {
  }
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
      icon: 'pi pi-sign-out',
      command: ()=>{
        localStorage.clear();
        this.router.navigate(['login'])
}
    }]
   role : string[] = [];
   email : string = '';
  checkUserDetails(){
    const authResp = localStorage.getItem('user');
    if(authResp){
      const user = JSON.parse(authResp);
      this.email = user.customerDTO.email;
      this.role = user.customerDTO.roles;
      console.log(user.customerDTO)
    }
  }

  ngOnInit(): void {
    this.checkUserDetails();
  }

}
