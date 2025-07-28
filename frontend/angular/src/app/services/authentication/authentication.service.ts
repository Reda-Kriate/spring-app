import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthenticationRequest} from '../../models/authentication-request';
import {Observable} from 'rxjs';
import {authenticationResponse} from '../../models/authentication-response';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private readonly url = `${environment.api.baseUrl}/${environment.api.authUrl}`;
  constructor(private http : HttpClient) {}
  login(authRequest:AuthenticationRequest):Observable<authenticationResponse> {
    return this.http.post<authenticationResponse>(this.url,authRequest);
  }
}
