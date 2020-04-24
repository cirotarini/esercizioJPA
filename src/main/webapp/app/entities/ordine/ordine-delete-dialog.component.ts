import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrdine } from 'app/shared/model/ordine.model';
import { OrdineService } from './ordine.service';

@Component({
  templateUrl: './ordine-delete-dialog.component.html'
})
export class OrdineDeleteDialogComponent {
  ordine?: IOrdine;

  constructor(protected ordineService: OrdineService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ordineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ordineListModification');
      this.activeModal.close();
    });
  }
}
