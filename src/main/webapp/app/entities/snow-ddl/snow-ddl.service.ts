import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISnowDDL } from 'app/shared/model/snow-ddl.model';
import { ISnowDDLProcessStatus } from 'app/shared/model/snow-ddl-process-status.model';
import { ISnowDDLJobStatus } from 'app/shared/model/snow-ddl-job-status.model';

type EntityResponseType = HttpResponse<ISnowDDL>;
type EntityArrayResponseType = HttpResponse<ISnowDDL[]>;
type ReportArrayResponseType = HttpResponse<ISnowDDLProcessStatus[]>;
type JobArrayResponseType = HttpResponse<ISnowDDLJobStatus[]>;

@Injectable({ providedIn: 'root' })
export class SnowDDLService {
  public resourceUrl = SERVER_API_URL + 'api/snow-ddl';

  constructor(protected http: HttpClient) {}

  create(snowDDL: ISnowDDL): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snowDDL);
    return this.http
      .post<ISnowDDL>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(snowDDL: ISnowDDL): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snowDDL);
    return this.http
      .put<ISnowDDL>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISnowDDL>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISnowDDL[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDDLList(snowDDL: ISnowDDL): Observable<EntityResponseType> {
    return this.http.post<ISnowDDL>(`${this.resourceUrl}/retrieveTableList`, snowDDL, { observe: 'response' });
  }

  convertDDL(snowDDL: ISnowDDL): Observable<EntityResponseType> {
    return this.http.post<ISnowDDL>(`${this.resourceUrl}/sendDDLtoConvert`, snowDDL, { observe: 'response' });
  }

  getProcessStatus(id: number): Observable<ReportArrayResponseType> {
    return this.http.get<ISnowDDLProcessStatus[]>(`${this.resourceUrl}/Reports/${id}`, { observe: 'response' });
  }

  getJobStatus(id: number): Observable<JobArrayResponseType> {
    return this.http.get<ISnowDDLJobStatus[]>(`${this.resourceUrl}/jobStatus/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(snowDDL: ISnowDDL): ISnowDDL {
    const copy: ISnowDDL = Object.assign({}, snowDDL, {
      createdDate: snowDDL.createdDate != null && snowDDL.createdDate.isValid() ? snowDDL.createdDate.toJSON() : null,
      lastModifiedDate: snowDDL.lastModifiedDate != null && snowDDL.lastModifiedDate.isValid() ? snowDDL.lastModifiedDate.toJSON() : null
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
      res.body.forEach((snowDDL: ISnowDDL) => {
        snowDDL.createdDate = snowDDL.createdDate != null ? moment(snowDDL.createdDate) : null;
        snowDDL.lastModifiedDate = snowDDL.lastModifiedDate != null ? moment(snowDDL.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
