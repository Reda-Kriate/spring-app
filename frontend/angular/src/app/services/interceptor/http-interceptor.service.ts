import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {authenticationResponse} from '../../models/authentication-response';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const userStocked = localStorage.getItem('user');
    if (userStocked) {
      const authResponse: authenticationResponse = JSON.parse(userStocked);
      const token = authResponse.token;
      if (token) {
        const authReq = req.clone({
          headers: new HttpHeaders({
            Authorization: 'Bearer '+token
          })
        });
        return next.handle(authReq);
      }
    }
    return next.handle(req);
  }
}
