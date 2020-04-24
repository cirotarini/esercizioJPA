import { Moment } from 'moment';
import { ICliente } from 'app/shared/model/cliente.model';

export interface IOrdine {
  id?: number;
  data?: Moment;
  cliente?: ICliente;
}

export class Ordine implements IOrdine {
  constructor(public id?: number, public data?: Moment, public cliente?: ICliente) {}
}
