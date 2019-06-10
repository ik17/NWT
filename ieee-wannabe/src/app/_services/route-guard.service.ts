import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable()
export class RouteGuardService implements CanActivate{

  constructor(public router: Router) {}
  canActivate(route: ActivatedRouteSnapshot): boolean {

    const expectedRole = route.data.expectedRole;

    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(localStorage.getItem("id_token"));
    return (decodedToken.role == expectedRole);

    }
}
