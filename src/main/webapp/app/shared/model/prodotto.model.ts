export interface IProdotto {
  id?: number;
  descrizione?: string;
}

export class Prodotto implements IProdotto {
  constructor(public id?: number, public descrizione?: string) {}
}
