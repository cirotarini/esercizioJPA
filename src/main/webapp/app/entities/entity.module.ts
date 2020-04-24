import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.EsercizioJpa032020ClienteModule)
      },
      {
        path: 'prodotto',
        loadChildren: () => import('./prodotto/prodotto.module').then(m => m.EsercizioJpa032020ProdottoModule)
      },
      {
        path: 'ordine',
        loadChildren: () => import('./ordine/ordine.module').then(m => m.EsercizioJpa032020OrdineModule)
      },
      {
        path: 'riga-ordine',
        loadChildren: () => import('./riga-ordine/riga-ordine.module').then(m => m.EsercizioJpa032020RigaOrdineModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EsercizioJpa032020EntityModule {}
