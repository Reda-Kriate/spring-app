import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MyCaluculatorService {

  sum(value:number , value1 : number) : number {
    return +value + +value1
  }
  sub(value : number , value1 : number) : number {
    return value - value1
  }
  mul(value : number , value1 : number) : number {
    return value * value1
  }
  div(value : number , value1 : number) : number {
    return value / value1
  }

  constructor() { }
}
