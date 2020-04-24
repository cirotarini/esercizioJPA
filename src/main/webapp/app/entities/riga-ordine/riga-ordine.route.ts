import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRigaOrdine, RigaOrdine } from 'app/shared/model/riga-ordine.model';
import { RigaOrdineService } from './riga-ordine.service';
import { RigaOrdineComponent } from './riga-ordine.component';
import { RigaOrdineDetailComponent } from './riga-ordine-detail.component';
import { RigaOrdineUpdateComponent } from './riga-ordine-update.component';

@Injectable({ providedIn: 'root' })
export class RigaOrdineResolve implements Resolve<IRigaOrdine> {
  constructor(private service: RigaOrdineService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRigaOrdine> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rigaOrdine: HttpResponse<RigaOrdine>) => {
          if (rigaOrdine.body) {
            return of(rigaOrdine.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RigaOrdine());
  }
}

export const rigaOrdineRoute: Routes = [
  {
    path: '',
    component: RigaOrdineComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RigaOrdines'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RigaOrdineDetailComponent,
    resolve: {
      rigaOrdine: RigaOrdineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RigaOrdines'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RigaOrdineUpdateComponent,
    resolve: {
      rigaOrdine: RigaOrdineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RigaOrdines'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RigaOrdineUpdateComponent,
    resolve: {
      rigaOrdine: RigaOrdineResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RigaOrdines'
    },
    canActivate: [UserRouteAccessService]
  }
];
