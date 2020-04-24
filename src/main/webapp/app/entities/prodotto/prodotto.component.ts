import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProdotto } from 'app/shared/model/prodotto.model';
import { ProdottoService } from './prodotto.service';
import { ProdottoDeleteDialogComponent } from './prodotto-delete-dialog.component';

@Component({
  selector: 'jhi-prodotto',
  templateUrl: './prodotto.component.html'
})
export class ProdottoComponent implements OnInit, OnDestroy {
  prodottos?: IProdotto[];
  eventSubscriber?: Subscription;

  constructor(protected prodottoService: ProdottoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.prodottoService.query().subscribe((res: HttpResponse<IProdotto[]>) => {
      this.prodottos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProdottos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProdotto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProdottos(): void {
    this.eventSubscriber = this.eventManager.subscribe('prodottoListModification', () => this.loadAll());
  }

  delete(prodotto: IProdotto): void {
    const modalRef = this.modalService.open(ProdottoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prodotto = prodotto;
  }
}
