import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EsercizioJpa032020SharedModule } from 'app/shared/shared.module';

import { LogsComponent } from './logs.component';

import { logsRoute } from './logs.route';

@NgModule({
  imports: [EsercizioJpa032020SharedModule, RouterModule.forChild([logsRoute])],
  declarations: [LogsComponent]
})
export class LogsModule {}
