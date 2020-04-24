import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRigaOrdine } from 'app/shared/model/riga-ordine.model';
import { RigaOrdineService } from './riga-ordine.service';

@Component({
  templateUrl: './riga-ordine-delete-dialog.component.html'
})
export class RigaOrdineDeleteDialogComponent {
  rigaOrdine?: IRigaOrdine;

  constructor(
    protected rigaOrdineService: RigaOrdineService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rigaOrdineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rigaOrdineListModification');
      this.activeModal.close();
    });
  }
}
