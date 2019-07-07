import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMigrationProcess } from 'app/shared/model/migration-process.model';

type EntityResponseType = HttpResponse<IMigrationProcess>;
type EntityArrayResponseType = HttpResponse<IMigrationProcess[]>;

@Injectable({ providedIn: 'root' })
export class MigrationProcessService {
  public resourceUrl = SERVER_API_URL + 'api/migration-processes';

  constructor(protected http: HttpClient) {}

  create(migrationProcess: IMigrationProcess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(migrationProcess);
    return this.http
      .post<IMigrationProcess>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(migrationProcess: IMigrationProcess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(migrationProcess);
    return this.http
      .put<IMigrationProcess>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMigrationProcess>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMigrationProcess[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTableList(migrationProcess: IMigrationProcess): Observable<EntityResponseType> {
    return this.http.post<IMigrationProcess>(`${this.resourceUrl}/retrieveTableList`, migrationProcess, { observe: 'response' });
  }

  protected convertDateFromClient(migrationProcess: IMigrationProcess): IMigrationProcess {
    const copy: IMigrationProcess = Object.assign({}, migrationProcess, {
      createdDate:
        migrationProcess.createdDate != null && migrationProcess.createdDate.isValid() ? migrationProcess.createdDate.toJSON() : null,
      lastModifiedDate:
        migrationProcess.lastModifiedDate != null && migrationProcess.lastModifiedDate.isValid()
          ? migrationProcess.lastModifiedDate.toJSON()
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
      res.body.forEach((migrationProcess: IMigrationProcess) => {
        migrationProcess.createdDate = migrationProcess.createdDate != null ? moment(migrationProcess.createdDate) : null;
        migrationProcess.lastModifiedDate = migrationProcess.lastModifiedDate != null ? moment(migrationProcess.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
