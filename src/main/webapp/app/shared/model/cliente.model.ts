export interface ICliente {
  id?: number;
  descrizione?: string;
}

export class Cliente implements ICliente {
  constructor(public id?: number, public descrizione?: string) {}
}
