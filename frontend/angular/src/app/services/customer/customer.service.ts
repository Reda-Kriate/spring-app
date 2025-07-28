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
    let headers : HttpHeaders = new HttpHeaders();
    headers = headers.set('Authorization','Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6InJlZGFAaG90bWFpbC5jb20iLCJpc3MiOiJodHRwczovL3JlZGFrcmlhdGUuY29tIiwiaWF0IjoxNzUzNjY0ODcyLCJleHAiOjE3NTQ5NjA4NzJ9.afCbvesCpYwPgJzHglbfZSvYAFEbD_Omdnd4hM443-0')
    return this.http.get<CustomerDTO[]>(this.url,
      {
        headers
      });
  }

}
