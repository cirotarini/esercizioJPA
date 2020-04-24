import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRigaOrdine } from 'app/shared/model/riga-ordine.model';

type EntityResponseType = HttpResponse<IRigaOrdine>;
type EntityArrayResponseType = HttpResponse<IRigaOrdine[]>;

@Injectable({ providedIn: 'root' })
export class RigaOrdineService {
  public resourceUrl = SERVER_API_URL + 'api/riga-ordines';

  constructor(protected http: HttpClient) {}

  create(rigaOrdine: IRigaOrdine): Observable<EntityResponseType> {
    return this.http.post<IRigaOrdine>(this.resourceUrl, rigaOrdine, { observe: 'response' });
  }

  update(rigaOrdine: IRigaOrdine): Observable<EntityResponseType> {
    return this.http.put<IRigaOrdine>(this.resourceUrl, rigaOrdine, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRigaOrdine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRigaOrdine[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
