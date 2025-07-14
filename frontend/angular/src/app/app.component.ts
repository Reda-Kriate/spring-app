import { Component } from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import { FirstComponent } from './first/first.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FirstComponent, FormsModule, RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'angular';
  result : number = 0;
  constructor(
    private router:Router
  ) {}

  changeResult(res: number) {
    this.result = res;
  }

  clickRoute() {
    this.router.navigate(['page-3']);
  }
}
