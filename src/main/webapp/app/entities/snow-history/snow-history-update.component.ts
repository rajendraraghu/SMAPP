import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISnowHistory, SnowHistory } from 'app/shared/model/snow-history.model';
import { SnowHistoryService } from './snow-history.service';
import { ISourceConnection } from 'app/shared/model/source-connection.model';
import { SourceConnectionService } from 'app/entities/source-connection';
import { ISnowflakeConnection } from 'app/shared/model/snowflake-connection.model';
import { SnowflakeConnectionService } from 'app/entities/snowflake-connection';

@Component({
  selector: 'jhi-snow-history-update',
  templateUrl: './snow-history-update.component.html'
})
export class SnowHistoryUpdateComponent implements OnInit {
  isSaving: boolean;

  sourceconnections: ISourceConnection[];

  snowflakeconnections: ISnowflakeConnection[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.maxLength(650)]],
    type: [],
    tablesToMigrate: [null, [Validators.maxLength(3200)]],
    lastStatus: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
    sourceConnectionId: [null, Validators.required],
    snowflakeConnectionId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected snowHistoryService: SnowHistoryService,
    protected sourceConnectionService: SourceConnectionService,
    protected snowflakeConnectionService: SnowflakeConnectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ snowHistory }) => {
      this.updateForm(snowHistory);
    });
    this.sourceConnectionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISourceConnection[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISourceConnection[]>) => response.body)
      )
      .subscribe((res: ISourceConnection[]) => (this.sourceconnections = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.snowflakeConnectionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISnowflakeConnection[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISnowflakeConnection[]>) => response.body)
      )
      .subscribe((res: ISnowflakeConnection[]) => (this.snowflakeconnections = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(snowHistory: ISnowHistory) {
    this.editForm.patchValue({
      id: snowHistory.id,
      name: snowHistory.name,
      description: snowHistory.description,
      type: snowHistory.type,
      tablesToMigrate: snowHistory.tablesToMigrate,
      lastStatus: snowHistory.lastStatus,
      // createdBy: snowHistory.createdBy,
      // createdDate: snowHistory.createdDate != null ? snowHistory.createdDate.format(DATE_TIME_FORMAT) : null,
      // lastModifiedBy: snowHistory.lastModifiedBy,
      // lastModifiedDate: snowHistory.lastModifiedDate != null ? snowHistory.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      sourceConnectionId: snowHistory.sourceConnectionId,
      snowflakeConnectionId: snowHistory.snowflakeConnectionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const snowHistory = this.createFromForm();
    if (snowHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.snowHistoryService.update(snowHistory));
    } else {
      this.subscribeToSaveResponse(this.snowHistoryService.create(snowHistory));
    }
  }

  private createFromForm(): ISnowHistory {
    return {
      ...new SnowHistory(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      type: this.editForm.get(['type']).value,
      tablesToMigrate: this.editForm.get(['tablesToMigrate']).value,
      lastStatus: this.editForm.get(['lastStatus']).value,
      // createdBy: this.editForm.get(['createdBy']).value,
      // createdDate:
      //   this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      // lastModifiedBy: this.editForm.get(['lastModifiedBy']).value,
      // lastModifiedDate:
      //   this.editForm.get(['lastModifiedDate']).value != null
      //     ? moment(this.editForm.get(['lastModifiedDate']).value, DATE_TIME_FORMAT)
      //     : undefined,
      sourceConnectionId: this.editForm.get(['sourceConnectionId']).value,
      snowflakeConnectionId: this.editForm.get(['snowflakeConnectionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISnowHistory>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSourceConnectionById(index: number, item: ISourceConnection) {
    return item.id;
  }

  trackSnowflakeConnectionById(index: number, item: ISnowflakeConnection) {
    return item.id;
  }
}
