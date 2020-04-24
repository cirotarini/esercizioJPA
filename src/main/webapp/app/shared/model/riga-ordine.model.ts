import { IOrdine } from 'app/shared/model/ordine.model';
import { IProdotto } from 'app/shared/model/prodotto.model';

export interface IRigaOrdine {
  id?: number;
  quantita?: number;
  ordine?: IOrdine;
  prodotto?: IProdotto;
}

export class RigaOrdine implements IRigaOrdine {
  constructor(public id?: number, public quantita?: number, public ordine?: IOrdine, public prodotto?: IProdotto) {}
}
