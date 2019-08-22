import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISnowHistory } from 'app/shared/model/snow-history.model';
import { ISnowHistoryProcessStatus } from 'app/shared/model/snow-history-process-status.model';
import { ISnowHistoryJobStatus } from 'app/shared/model/snow-history-job-status.model';

type EntityResponseType = HttpResponse<ISnowHistory>;
type EntityArrayResponseType = HttpResponse<ISnowHistory[]>;
type ReportArrayResponseType = HttpResponse<ISnowHistoryProcessStatus[]>;
type JobArrayResponseType = HttpResponse<ISnowHistoryJobStatus[]>;

@Injectable({ providedIn: 'root' })
export class SnowHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/snow-histories';

  constructor(protected http: HttpClient) {}

  create(snowHistory: ISnowHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snowHistory);
    return this.http
      .post<ISnowHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(snowHistory: ISnowHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snowHistory);
    return this.http
      .put<ISnowHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISnowHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISnowHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTableList(snowHistory: ISnowHistory): Observable<EntityResponseType> {
    return this.http.post<ISnowHistory>(`${this.resourceUrl}/retrieveTableList`, snowHistory, { observe: 'response' });
  }

  sendTableList(snowHistory: ISnowHistory): Observable<EntityResponseType> {
    return this.http.post<ISnowHistory>(`${this.resourceUrl}/sendTableListforHistProcess`, snowHistory, { observe: 'response' });
  }

  getProcessStatus(id: number): Observable<ReportArrayResponseType> {
    return this.http.get<ISnowHistoryProcessStatus[]>(`${this.resourceUrl}/Reports/${id}`, { observe: 'response' });
  }

  getJobStatus(id: number): Observable<JobArrayResponseType> {
    return this.http.get<ISnowHistoryJobStatus[]>(`${this.resourceUrl}/jobStatus/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(snowHistory: ISnowHistory): ISnowHistory {
    const copy: ISnowHistory = Object.assign({}, snowHistory, {
      createdDate: snowHistory.createdDate != null && snowHistory.createdDate.isValid() ? snowHistory.createdDate.toJSON() : null,
      lastModifiedDate:
        snowHistory.lastModifiedDate != null && snowHistory.lastModifiedDate.isValid() ? snowHistory.lastModifiedDate.toJSON() : null
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
      res.body.forEach((snowHistory: ISnowHistory) => {
        snowHistory.createdDate = snowHistory.createdDate != null ? moment(snowHistory.createdDate) : null;
        snowHistory.lastModifiedDate = snowHistory.lastModifiedDate != null ? moment(snowHistory.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
