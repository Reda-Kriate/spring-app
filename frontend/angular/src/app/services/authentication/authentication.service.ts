import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthenticationRequest} from '../../models/authentication-request';
import {Observable} from 'rxjs';
import {authenticationResponse} from '../../models/authentication-response';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private http : HttpClient) {}
  login(authRequest:AuthenticationRequest):Observable<authenticationResponse> {
    return this.http.post<authenticationResponse>('http://localhost:8081/api/v1/auth/login',authRequest);
  }
}
