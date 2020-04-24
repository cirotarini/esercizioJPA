import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRigaOrdine } from 'app/shared/model/riga-ordine.model';
import { RigaOrdineService } from './riga-ordine.service';
import { RigaOrdineDeleteDialogComponent } from './riga-ordine-delete-dialog.component';

@Component({
  selector: 'jhi-riga-ordine',
  templateUrl: './riga-ordine.component.html'
})
export class RigaOrdineComponent implements OnInit, OnDestroy {
  rigaOrdines?: IRigaOrdine[];
  eventSubscriber?: Subscription;

  constructor(protected rigaOrdineService: RigaOrdineService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.rigaOrdineService.query().subscribe((res: HttpResponse<IRigaOrdine[]>) => {
      this.rigaOrdines = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRigaOrdines();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRigaOrdine): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRigaOrdines(): void {
    this.eventSubscriber = this.eventManager.subscribe('rigaOrdineListModification', () => this.loadAll());
  }

  delete(rigaOrdine: IRigaOrdine): void {
    const modalRef = this.modalService.open(RigaOrdineDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rigaOrdine = rigaOrdine;
  }
}
