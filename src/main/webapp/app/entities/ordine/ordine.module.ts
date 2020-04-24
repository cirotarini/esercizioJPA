import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsercizioJpa032020SharedModule } from 'app/shared/shared.module';
import { OrdineComponent } from './ordine.component';
import { OrdineDetailComponent } from './ordine-detail.component';
import { OrdineUpdateComponent } from './ordine-update.component';
import { OrdineDeleteDialogComponent } from './ordine-delete-dialog.component';
import { ordineRoute } from './ordine.route';

@NgModule({
  imports: [EsercizioJpa032020SharedModule, RouterModule.forChild(ordineRoute)],
  declarations: [OrdineComponent, OrdineDetailComponent, OrdineUpdateComponent, OrdineDeleteDialogComponent],
  entryComponents: [OrdineDeleteDialogComponent]
})
export class EsercizioJpa032020OrdineModule {}
