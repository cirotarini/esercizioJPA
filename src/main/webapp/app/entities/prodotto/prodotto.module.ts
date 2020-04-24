import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsercizioJpa032020SharedModule } from 'app/shared/shared.module';
import { ProdottoComponent } from './prodotto.component';
import { ProdottoDetailComponent } from './prodotto-detail.component';
import { ProdottoUpdateComponent } from './prodotto-update.component';
import { ProdottoDeleteDialogComponent } from './prodotto-delete-dialog.component';
import { prodottoRoute } from './prodotto.route';

@NgModule({
  imports: [EsercizioJpa032020SharedModule, RouterModule.forChild(prodottoRoute)],
  declarations: [ProdottoComponent, ProdottoDetailComponent, ProdottoUpdateComponent, ProdottoDeleteDialogComponent],
  entryComponents: [ProdottoDeleteDialogComponent]
})
export class EsercizioJpa032020ProdottoModule {}
