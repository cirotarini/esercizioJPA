import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrdine, Ordine } from 'app/shared/model/ordine.model';
import { OrdineService } from './ordine.service';
import { OrdineComponent } from './ordine.component';
import { OrdineDetailComponent } from './ordine-detail.component';
import { OrdineUpdateComponent } from './ordine-update.component';

@Injectable({ providedIn: 'root' })
export class OrdineResolve implements Resolve<IOrdine> {
  constructor(private service: OrdineService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrdine> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ordine: HttpResponse<Ordine>) => {
          if (ordine.body) {
            return of(ordine.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ordine());
  }
}

export const ordineRoute: Routes = [
  {
    path: '',
    component: OrdineComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Ordines'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrdineDetailComponent,
    resolve: {
      ordine: OrdineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Ordines'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrdineUpdateComponent,
    resolve: {
      ordine: OrdineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Ordines'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrdineUpdateComponent,
    resolve: {
      ordine: OrdineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Ordines'
    },
    canActivate: [UserRouteAccessService]
  }
];
