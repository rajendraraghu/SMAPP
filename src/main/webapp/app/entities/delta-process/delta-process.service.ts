import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeltaProcess } from 'app/shared/model/delta-process.model';
import { IDeltaProcessStatus } from 'app/shared/model/delta-process-status.model';
import { IDeltaProcessJobStatus } from 'app/shared/model/delta-process-job-status.model';

type EntityResponseType = HttpResponse<IDeltaProcess>;
type EntityArrayResponseType = HttpResponse<IDeltaProcess[]>;
type ReportArrayResponseType = HttpResponse<IDeltaProcessStatus[]>;
type JobArrayResponseType = HttpResponse<IDeltaProcessJobStatus[]>;

@Injectable({ providedIn: 'root' })
export class DeltaProcessService {
  public resourceUrl = SERVER_API_URL + 'api/delta-processes';
  params: HttpParams = new HttpParams();

  constructor(protected http: HttpClient) {}

  create(deltaProcess: IDeltaProcess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deltaProcess);
    return this.http
      .post<IDeltaProcess>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deltaProcess: IDeltaProcess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deltaProcess);
    return this.http
      .put<IDeltaProcess>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeltaProcess>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeltaProcess[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTableList(deltaProcess: IDeltaProcess): Observable<EntityResponseType> {
    return this.http.post<IDeltaProcess>(`${this.resourceUrl}/retrieveTableList`, deltaProcess, { observe: 'response' });
  }

  getFileColumnList(deltaProcess: IDeltaProcess, fileName: string): Observable<EntityResponseType> {
    this.params = this.params.set('fileName', fileName);
    // return this.http.post<IMigrationProcess>(`${this.resourceUrl}/retrieveFileColumnList?fileName=` + fileName, migrationProcess, { observe: 'response' });
    return this.http.post<IDeltaProcess>(`${this.resourceUrl}/retrieveFileColumnList`, deltaProcess, {
      params: this.params,
      observe: 'response'
    });
  }

  sendTableList(deltaProcess: IDeltaProcess): Observable<EntityResponseType> {
    return this.http.post<IDeltaProcess>(`${this.resourceUrl}/sendTableListforHistProcess`, deltaProcess, { observe: 'response' });
  }

  getProcessStatus(id: number): Observable<ReportArrayResponseType> {
    return this.http.get<IDeltaProcessStatus[]>(`${this.resourceUrl}/Reports/${id}`, { observe: 'response' });
  }

  getJobStatus(id: number): Observable<JobArrayResponseType> {
    return this.http.get<IDeltaProcessJobStatus[]>(`${this.resourceUrl}/jobStatus/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(deltaProcess: IDeltaProcess): IDeltaProcess {
    const copy: IDeltaProcess = Object.assign({}, deltaProcess, {
      createdDate: deltaProcess.createdDate != null && deltaProcess.createdDate.isValid() ? deltaProcess.createdDate.toJSON() : null,
      lastModifiedDate:
        deltaProcess.lastModifiedDate != null && deltaProcess.lastModifiedDate.isValid() ? deltaProcess.lastModifiedDate.toJSON() : null
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
      res.body.forEach((deltaProcess: IDeltaProcess) => {
        deltaProcess.createdDate = deltaProcess.createdDate != null ? moment(deltaProcess.createdDate) : null;
        deltaProcess.lastModifiedDate = deltaProcess.lastModifiedDate != null ? moment(deltaProcess.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
