import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISourceConnection } from 'app/shared/model/source-connection.model';

type EntityResponseType = HttpResponse<ISourceConnection>;
type EntityArrayResponseType = HttpResponse<ISourceConnection[]>;

@Injectable({ providedIn: 'root' })
export class SourceConnectionService {
  public resourceUrl = SERVER_API_URL + 'api/source-connections';
  public testConnectionUrl = SERVER_API_URL + 'api/migration-processes/TestConnectionSource';

  constructor(protected http: HttpClient) {}

  create(sourceConnection: ISourceConnection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sourceConnection);
    return this.http
      .post<ISourceConnection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sourceConnection: ISourceConnection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sourceConnection);
    return this.http
      .put<ISourceConnection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISourceConnection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISourceConnection[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  testConnection(sourceConnection: ISourceConnection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sourceConnection);
    return this.http.post<ISourceConnection>(this.testConnectionUrl, copy, { observe: 'response' });
  }

  protected convertDateFromClient(sourceConnection: ISourceConnection): ISourceConnection {
    const copy: ISourceConnection = Object.assign({}, sourceConnection, {
      createdDate:
        sourceConnection.createdDate != null && sourceConnection.createdDate.isValid() ? sourceConnection.createdDate.toJSON() : null,
      lastModifiedDate:
        sourceConnection.lastModifiedDate != null && sourceConnection.lastModifiedDate.isValid()
          ? sourceConnection.lastModifiedDate.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
      res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sourceConnection: ISourceConnection) => {
        sourceConnection.createdDate = sourceConnection.createdDate != null ? moment(sourceConnection.createdDate) : null;
        sourceConnection.lastModifiedDate = sourceConnection.lastModifiedDate != null ? moment(sourceConnection.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
