import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRigaOrdine } from 'app/shared/model/riga-ordine.model';

@Component({
  selector: 'jhi-riga-ordine-detail',
  templateUrl: './riga-ordine-detail.component.html'
})
export class RigaOrdineDetailComponent implements OnInit {
  rigaOrdine: IRigaOrdine | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rigaOrdine }) => {
      this.rigaOrdine = rigaOrdine;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
