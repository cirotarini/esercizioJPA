import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrdine } from 'app/shared/model/ordine.model';

type EntityResponseType = HttpResponse<IOrdine>;
type EntityArrayResponseType = HttpResponse<IOrdine[]>;

@Injectable({ providedIn: 'root' })
export class OrdineService {
  public resourceUrl = SERVER_API_URL + 'api/ordines';

  constructor(protected http: HttpClient) {}

  create(ordine: IOrdine): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ordine);
    return this.http
      .post<IOrdine>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ordine: IOrdine): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ordine);
    return this.http
      .put<IOrdine>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrdine>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrdine[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ordine: IOrdine): IOrdine {
    const copy: IOrdine = Object.assign({}, ordine, {
      data: ordine.data && ordine.data.isValid() ? ordine.data.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ordine: IOrdine) => {
        ordine.data = ordine.data ? moment(ordine.data) : undefined;
      });
    }
    return res;
  }
}
