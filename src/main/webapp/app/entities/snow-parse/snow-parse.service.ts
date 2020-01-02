import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISnowParse } from 'app/shared/model/snow-parse.model';
import { ISnowParseProcessStatus } from 'app/shared/model/snow-parse-process-status.model';
import { ISnowParseJobStatus } from 'app/shared/model/snow-parse-job-status.model';

type EntityResponseType = HttpResponse<ISnowParse>;
type EntityArrayResponseType = HttpResponse<ISnowParse[]>;
type ReportArrayResponseType = HttpResponse<ISnowParseProcessStatus[]>;
type JobArrayResponseType = HttpResponse<ISnowParseJobStatus[]>;

@Injectable({ providedIn: 'root' })
export class SnowParseService {
  public resourceUrl = SERVER_API_URL + 'api/snow-parse';

  constructor(protected http: HttpClient) {}

  create(snowParse: ISnowParse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snowParse);
    return this.http
      .post<ISnowParse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(snowParse: ISnowParse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(snowParse);
    return this.http
      .put<ISnowParse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISnowParse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISnowParse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDDLList(snowParse: ISnowParse): Observable<EntityResponseType> {
    return this.http.post<ISnowParse>(`${this.resourceUrl}/retrieveTableList`, snowParse, { observe: 'response' });
  }

  convertDDL(snowParse: ISnowParse): Observable<EntityResponseType> {
    return this.http.post<ISnowParse>(`${this.resourceUrl}/sendDDLtoConvert`, snowParse, { observe: 'response' });
  }

  getProcessStatus(id: number): Observable<ReportArrayResponseType> {
    return this.http.get<ISnowParseProcessStatus[]>(`${this.resourceUrl}/Reports/${id}`, { observe: 'response' });
  }

  getJobStatus(id: number): Observable<JobArrayResponseType> {
    return this.http.get<ISnowParseJobStatus[]>(`${this.resourceUrl}/jobStatus/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(snowParse: ISnowParse): ISnowParse {
    const copy: ISnowParse = Object.assign({}, snowParse, {
      createdDate: snowParse.createdDate != null && snowParse.createdDate.isValid() ? snowParse.createdDate.toJSON() : null,
      lastModifiedDate:
        snowParse.lastModifiedDate != null && snowParse.lastModifiedDate.isValid() ? snowParse.lastModifiedDate.toJSON() : null
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
      res.body.forEach((snowParse: ISnowParse) => {
        snowParse.createdDate = snowParse.createdDate != null ? moment(snowParse.createdDate) : null;
        snowParse.lastModifiedDate = snowParse.lastModifiedDate != null ? moment(snowParse.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
