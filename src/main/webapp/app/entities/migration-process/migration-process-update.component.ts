import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IMigrationProcess, MigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessService } from './migration-process.service';
import { ISourceConnection } from 'app/shared/model/source-connection.model';
import { SourceConnectionService } from 'app/entities/source-connection';
import { ISnowflakeConnection } from 'app/shared/model/snowflake-connection.model';
import { SnowflakeConnectionService } from 'app/entities/snowflake-connection';

@Component({
  selector: 'jhi-migration-process-update',
  templateUrl: './migration-process-update.component.html'
})
export class MigrationProcessUpdateComponent implements OnInit {
  isSaving: boolean;

  sourceconnections: ISourceConnection[];

  snowflakeconnections: ISnowflakeConnection[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.maxLength(650)]],
    type: [],
    tablesToMigrate: [],
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
    protected migrationProcessService: MigrationProcessService,
    protected sourceConnectionService: SourceConnectionService,
    protected snowflakeConnectionService: SnowflakeConnectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      this.updateForm(migrationProcess);
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

  updateForm(migrationProcess: IMigrationProcess) {
    this.editForm.patchValue({
      id: migrationProcess.id,
      name: migrationProcess.name,
      description: migrationProcess.description,
      type: migrationProcess.type,
      tablesToMigrate: migrationProcess.tablesToMigrate,
      lastStatus: migrationProcess.lastStatus,
      createdBy: migrationProcess.createdBy,
      createdDate: migrationProcess.createdDate != null ? migrationProcess.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: migrationProcess.lastModifiedBy,
      lastModifiedDate: migrationProcess.lastModifiedDate != null ? migrationProcess.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      sourceConnectionId: migrationProcess.sourceConnectionId,
      snowflakeConnectionId: migrationProcess.snowflakeConnectionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const migrationProcess = this.createFromForm();
    if (migrationProcess.id !== undefined) {
      this.subscribeToSaveResponse(this.migrationProcessService.update(migrationProcess));
    } else {
      this.subscribeToSaveResponse(this.migrationProcessService.create(migrationProcess));
    }
  }

  private createFromForm(): IMigrationProcess {
    return {
      ...new MigrationProcess(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      type: this.editForm.get(['type']).value,
      tablesToMigrate: this.editForm.get(['tablesToMigrate']).value,
      lastStatus: this.editForm.get(['lastStatus']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy']).value,
      lastModifiedDate:
        this.editForm.get(['lastModifiedDate']).value != null
          ? moment(this.editForm.get(['lastModifiedDate']).value, DATE_TIME_FORMAT)
          : undefined,
      sourceConnectionId: this.editForm.get(['sourceConnectionId']).value,
      snowflakeConnectionId: this.editForm.get(['snowflakeConnectionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMigrationProcess>>) {
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
