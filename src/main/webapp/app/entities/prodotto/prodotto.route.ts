import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProdotto, Prodotto } from 'app/shared/model/prodotto.model';
import { ProdottoService } from './prodotto.service';
import { ProdottoComponent } from './prodotto.component';
import { ProdottoDetailComponent } from './prodotto-detail.component';
import { ProdottoUpdateComponent } from './prodotto-update.component';

@Injectable({ providedIn: 'root' })
export class ProdottoResolve implements Resolve<IProdotto> {
  constructor(private service: ProdottoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProdotto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((prodotto: HttpResponse<Prodotto>) => {
          if (prodotto.body) {
            return of(prodotto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Prodotto());
  }
}

export const prodottoRoute: Routes = [
  {
    path: '',
    component: ProdottoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Prodottos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProdottoDetailComponent,
    resolve: {
      prodotto: ProdottoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Prodottos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProdottoUpdateComponent,
    resolve: {
      prodotto: ProdottoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Prodottos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProdottoUpdateComponent,
    resolve: {
      prodotto: ProdottoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Prodottos'
    },
    canActivate: [UserRouteAccessService]
  }
];
