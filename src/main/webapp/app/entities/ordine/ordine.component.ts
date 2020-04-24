import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrdine } from 'app/shared/model/ordine.model';
import { OrdineService } from './ordine.service';
import { OrdineDeleteDialogComponent } from './ordine-delete-dialog.component';

@Component({
  selector: 'jhi-ordine',
  templateUrl: './ordine.component.html'
})
export class OrdineComponent implements OnInit, OnDestroy {
  ordines?: IOrdine[];
  eventSubscriber?: Subscription;

  constructor(protected ordineService: OrdineService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.ordineService.query().subscribe((res: HttpResponse<IOrdine[]>) => {
      this.ordines = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrdines();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrdine): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrdines(): void {
    this.eventSubscriber = this.eventManager.subscribe('ordineListModification', () => this.loadAll());
  }

  delete(ordine: IOrdine): void {
    const modalRef = this.modalService.open(OrdineDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ordine = ordine;
  }
}
