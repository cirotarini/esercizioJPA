import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IRigaOrdine, RigaOrdine } from 'app/shared/model/riga-ordine.model';
import { RigaOrdineService } from './riga-ordine.service';
import { IOrdine } from 'app/shared/model/ordine.model';
import { OrdineService } from 'app/entities/ordine/ordine.service';
import { IProdotto } from 'app/shared/model/prodotto.model';
import { ProdottoService } from 'app/entities/prodotto/prodotto.service';

type SelectableEntity = IOrdine | IProdotto;

@Component({
  selector: 'jhi-riga-ordine-update',
  templateUrl: './riga-ordine-update.component.html'
})
export class RigaOrdineUpdateComponent implements OnInit {
  isSaving = false;

  ordines: IOrdine[] = [];

  prodottos: IProdotto[] = [];

  editForm = this.fb.group({
    id: [],
    quantita: [null, [Validators.required]],
    ordine: [],
    prodotto: []
  });

  constructor(
    protected rigaOrdineService: RigaOrdineService,
    protected ordineService: OrdineService,
    protected prodottoService: ProdottoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rigaOrdine }) => {
      this.updateForm(rigaOrdine);

      this.ordineService
        .query()
        .pipe(
          map((res: HttpResponse<IOrdine[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IOrdine[]) => (this.ordines = resBody));

      this.prodottoService
        .query()
        .pipe(
          map((res: HttpResponse<IProdotto[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProdotto[]) => (this.prodottos = resBody));
    });
  }

  updateForm(rigaOrdine: IRigaOrdine): void {
    this.editForm.patchValue({
      id: rigaOrdine.id,
      quantita: rigaOrdine.quantita,
      ordine: rigaOrdine.ordine,
      prodotto: rigaOrdine.prodotto
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rigaOrdine = this.createFromForm();
    if (rigaOrdine.id !== undefined) {
      this.subscribeToSaveResponse(this.rigaOrdineService.update(rigaOrdine));
    } else {
      this.subscribeToSaveResponse(this.rigaOrdineService.create(rigaOrdine));
    }
  }

  private createFromForm(): IRigaOrdine {
    return {
      ...new RigaOrdine(),
      id: this.editForm.get(['id'])!.value,
      quantita: this.editForm.get(['quantita'])!.value,
      ordine: this.editForm.get(['ordine'])!.value,
      prodotto: this.editForm.get(['prodotto'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRigaOrdine>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
