import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EsercizioJpa032020SharedModule } from 'app/shared/shared.module';

import { DocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [EsercizioJpa032020SharedModule, RouterModule.forChild([docsRoute])],
  declarations: [DocsComponent]
})
export class DocsModule {}
