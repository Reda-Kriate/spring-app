import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CustomerDTO} from '../../models/customer-dto';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService{
  private readonly url  = `${environment.api.baseUrl}/${environment.api.customerUrl}`;
  constructor(
    private http : HttpClient
  ) {}

  findAllCustomers():Observable<CustomerDTO[]>{
    return this.http.get<CustomerDTO[]>(this.url);
  }
}
