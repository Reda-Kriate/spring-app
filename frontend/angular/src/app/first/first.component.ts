import {Component, EventEmitter, Output} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {MyCaluculatorService} from '../services/my-caluculator.service';

@Component({
  selector: 'app-first',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './first.component.html',
  styleUrl: './first.component.scss'
})
export class FirstComponent {
  value = 0;
  value1 = 0;
  @Output()
  resultPar: EventEmitter<number> = new EventEmitter<number>();
  result:number = 0;
  constructor(
    private readonly calcS : MyCaluculatorService
  ) {}

  sum() : number {
    this.result = this.calcS.sum(this.value,this.value1);
    this.resultPar.emit(this.result)
    return this.result
  }
  sub() : number {
    this.result = this.calcS.sub(this.value,this.value1);
    this.resultPar.emit(this.result)
    return this.result
  }
  mul() : number {
    this.result = this.calcS.mul(this.value,this.value1);
    this.resultPar.emit(this.result)
    return this.result
  }
  div() : number {
    this.result = this.calcS.div(this.value,this.value1);
    this.resultPar.emit(this.result)
    return this.result
  }

}
