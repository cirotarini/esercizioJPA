import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { EsercizioJpa032020SharedModule } from 'app/shared/shared.module';
import { EsercizioJpa032020CoreModule } from 'app/core/core.module';
import { EsercizioJpa032020AppRoutingModule } from './app-routing.module';
import { EsercizioJpa032020HomeModule } from './home/home.module';
import { EsercizioJpa032020EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    EsercizioJpa032020SharedModule,
    EsercizioJpa032020CoreModule,
    EsercizioJpa032020HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    EsercizioJpa032020EntityModule,
    EsercizioJpa032020AppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class EsercizioJpa032020AppModule {}
