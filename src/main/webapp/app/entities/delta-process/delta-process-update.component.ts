import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDeltaProcess, DeltaProcess } from 'app/shared/model/delta-process.model';
import { DeltaProcessService } from './delta-process.service';
import { ISourceConnection } from 'app/shared/model/source-connection.model';
import { SourceConnectionService } from 'app/entities/source-connection';
import { ISnowflakeConnection } from 'app/shared/model/snowflake-connection.model';
import { SnowflakeConnectionService } from 'app/entities/snowflake-connection';

@Component({
  selector: 'jhi-delta-process-update',
  templateUrl: './delta-process-update.component.html'
})
export class DeltaProcessUpdateComponent implements OnInit {
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
    protected deltaProcessService: DeltaProcessService,
    protected sourceConnectionService: SourceConnectionService,
    protected snowflakeConnectionService: SnowflakeConnectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ deltaProcess }) => {
      this.updateForm(deltaProcess);
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

  updateForm(deltaProcess: IDeltaProcess) {
    this.editForm.patchValue({
      id: deltaProcess.id,
      name: deltaProcess.name,
      description: deltaProcess.description,
      type: deltaProcess.type,
      tablesToMigrate: deltaProcess.tablesToMigrate,
      lastStatus: deltaProcess.lastStatus,
      // createdBy: deltaProcess.createdBy,
      // createdDate: deltaProcess.createdDate != null ? deltaProcess.createdDate.format(DATE_TIME_FORMAT) : null,
      // lastModifiedBy: deltaProcess.lastModifiedBy,
      // lastModifiedDate: deltaProcess.lastModifiedDate != null ? deltaProcess.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      sourceConnectionId: deltaProcess.sourceConnectionId,
      snowflakeConnectionId: deltaProcess.snowflakeConnectionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const deltaProcess = this.createFromForm();
    if (deltaProcess.id !== undefined) {
      this.subscribeToSaveResponse(this.deltaProcessService.update(deltaProcess));
    } else {
      this.subscribeToSaveResponse(this.deltaProcessService.create(deltaProcess));
    }
  }

  private createFromForm(): IDeltaProcess {
    return {
      ...new DeltaProcess(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeltaProcess>>) {
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
