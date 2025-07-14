import { Routes } from '@angular/router';
import {Page1Component} from './pages/page-1/page-1.component';
import {Page2Component} from './pages/page-2/page-2.component';
import {Page3Component} from './pages/page-3/page-3.component';


export const routes: Routes = [
  { path:'page-1', component: Page1Component },
  { path:'page-2', component: Page2Component },
  { path:'page-3', component: Page3Component }
];
