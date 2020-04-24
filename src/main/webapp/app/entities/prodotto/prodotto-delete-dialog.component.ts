import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProdotto } from 'app/shared/model/prodotto.model';
import { ProdottoService } from './prodotto.service';

@Component({
  templateUrl: './prodotto-delete-dialog.component.html'
})
export class ProdottoDeleteDialogComponent {
  prodotto?: IProdotto;

  constructor(protected prodottoService: ProdottoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prodottoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('prodottoListModification');
      this.activeModal.close();
    });
  }
}
