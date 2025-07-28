import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import {JwtHelperService} from '@auth0/angular-jwt';
import {authenticationResponse} from '../../models/authentication-response';

@Injectable({
  providedIn: 'root'
})
export class GuardService implements CanActivate {
  constructor(
    private router: Router
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    const userStocked = localStorage.getItem('user');
    if (userStocked) {
      const authResponse: authenticationResponse = JSON.parse(userStocked);
      const token = authResponse.token;
      if (token) {
        const jwtHelper = new JwtHelperService();
        const isTokenNotExpired = !jwtHelper.isTokenExpired(token);
        if (isTokenNotExpired) {
          return true;
        }else{
          this.router.navigate(['login']);
        }
      }
    }

    this.router.navigate(['login']);
    return false;
  }
}
