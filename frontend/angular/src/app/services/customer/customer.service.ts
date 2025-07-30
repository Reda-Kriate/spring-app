import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CustomerDTO} from '../../models/customer-dto';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {customerRegistrationRequest} from '../../models/customerRegistrationRequest';
import {CustomerUpdate} from '../../models/customerUpdate';

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
  saveCustomer(req : customerRegistrationRequest):Observable<void>{
    return this.http.post<void>(this.url,req);
  }
  deleteCustomer(id: number | undefined):Observable<void>{
    return this.http.delete<void>(`${this.url}/${id}`);
  }
  updateCustomer(id:number | undefined, update : CustomerUpdate):Observable<void>{
    return this.http.put<void>(`${this.url}/${id}`, update);
  }
}
