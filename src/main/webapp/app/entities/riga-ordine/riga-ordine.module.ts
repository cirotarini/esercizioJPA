import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsercizioJpa032020SharedModule } from 'app/shared/shared.module';
import { RigaOrdineComponent } from './riga-ordine.component';
import { RigaOrdineDetailComponent } from './riga-ordine-detail.component';
import { RigaOrdineUpdateComponent } from './riga-ordine-update.component';
import { RigaOrdineDeleteDialogComponent } from './riga-ordine-delete-dialog.component';
import { rigaOrdineRoute } from './riga-ordine.route';

@NgModule({
  imports: [EsercizioJpa032020SharedModule, RouterModule.forChild(rigaOrdineRoute)],
  declarations: [RigaOrdineComponent, RigaOrdineDetailComponent, RigaOrdineUpdateComponent, RigaOrdineDeleteDialogComponent],
  entryComponents: [RigaOrdineDeleteDialogComponent]
})
export class EsercizioJpa032020RigaOrdineModule {}
