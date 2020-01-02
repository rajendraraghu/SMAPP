import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISnowflakeConnection } from 'app/shared/model/snowflake-connection.model';

type EntityResponseType = HttpResponse<ISnowflakeConnection>;
type EntityArrayResponseType = HttpResponse<ISnowflakeConnection[]>;

@Injectable({ providedIn: 'root' })
export class SnowflakeConnectionService {
  public resourceUrl = SERVER_API_URL + 'api/snowflake-connections';
  public testConnectionUrl = SERVER_API_URL + 'api/migration-processes/TestConnectionDest';

  constructor(protected http: HttpClient) {}

  create(snowflakeConnection: ISnowflakeConnection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snowflakeConnection);
    return this.http
      .post<ISnowflakeConnection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(snowflakeConnection: ISnowflakeConnection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snowflakeConnection);
    return this.http
      .put<ISnowflakeConnection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISnowflakeConnection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISnowflakeConnection[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  testConnection(snowflakeConnection: ISnowflakeConnection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snowflakeConnection);
    return this.http.post<ISnowflakeConnection>(this.testConnectionUrl, copy, { observe: 'response' });
  }

  protected convertDateFromClient(snowflakeConnection: ISnowflakeConnection): ISnowflakeConnection {
    const copy: ISnowflakeConnection = Object.assign({}, snowflakeConnection, {
      createdDate:
        snowflakeConnection.createdDate != null && snowflakeConnection.createdDate.isValid()
          ? snowflakeConnection.createdDate.toJSON()
          : null,
      lastModifiedDate:
        snowflakeConnection.lastModifiedDate != null && snowflakeConnection.lastModifiedDate.isValid()
          ? snowflakeConnection.lastModifiedDate.toJSON()
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
      res.body.forEach((snowflakeConnection: ISnowflakeConnection) => {
        snowflakeConnection.createdDate = snowflakeConnection.createdDate != null ? moment(snowflakeConnection.createdDate) : null;
        snowflakeConnection.lastModifiedDate =
          snowflakeConnection.lastModifiedDate != null ? moment(snowflakeConnection.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
