import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISnowflakeConnection, SnowflakeConnection } from 'app/shared/model/snowflake-connection.model';
import { SnowflakeConnectionService } from './snowflake-connection.service';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-snowflake-connection-update',
  templateUrl: './snowflake-connection-update.component.html'
})
export class SnowflakeConnectionUpdateComponent implements OnInit {
  isSaving: boolean;
  valid: boolean;
  testDisable: boolean;
  snowflakeConnection: ISnowflakeConnection;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    url: [null, [Validators.required, Validators.maxLength(1200)]],
    username: [null, [Validators.required]],
    password: [null, [Validators.required]],
    acct: [],
    warehouse: [],
    database: [null, [Validators.required]],
    schema: [],
    valid: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: []
  });

  constructor(
    protected snowflakeConnectionService: SnowflakeConnectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.valid = false;
    this.activatedRoute.data.subscribe(({ snowflakeConnection }) => {
      this.updateForm(snowflakeConnection);
      this.snowflakeConnection = snowflakeConnection;
    });
  }

  updateForm(snowflakeConnection: ISnowflakeConnection) {
    this.editForm.patchValue({
      id: snowflakeConnection.id,
      name: snowflakeConnection.name,
      description: snowflakeConnection.description,
      url: snowflakeConnection.url,
      username: snowflakeConnection.username,
      password: snowflakeConnection.password,
      acct: snowflakeConnection.acct,
      warehouse: snowflakeConnection.warehouse,
      database: snowflakeConnection.database,
      schema: snowflakeConnection.schema,
      valid: snowflakeConnection.valid
      // createdBy: snowflakeConnection.createdBy,
      // createdDate: snowflakeConnection.createdDate != null ? snowflakeConnection.createdDate.format(DATE_TIME_FORMAT) : null,
      // lastModifiedBy: snowflakeConnection.lastModifiedBy,
      // lastModifiedDate: snowflakeConnection.lastModifiedDate != null ? snowflakeConnection.lastModifiedDate.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    // this.testConnection();
    const snowflakeConnection = this.createFromForm();
    snowflakeConnection.valid = snowflakeConnection.valid;
    if (snowflakeConnection.id !== undefined) {
      this.subscribeToSaveResponse(this.snowflakeConnectionService.update(snowflakeConnection));
    } else {
      this.subscribeToSaveResponse(this.snowflakeConnectionService.create(snowflakeConnection));
    }
  }

  private createFromForm(): ISnowflakeConnection {
    return {
      ...new SnowflakeConnection(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      url: this.editForm.get(['url']).value,
      username: this.editForm.get(['username']).value,
      password: this.editForm.get(['password']).value,
      acct: this.editForm.get(['acct']).value,
      warehouse: this.editForm.get(['warehouse']).value,
      database: this.editForm.get(['database']).value,
      schema: this.editForm.get(['schema']).value,
      valid: this.editForm.get(['valid']).value
      // createdBy: this.editForm.get(['createdBy']).value,
      // createdDate:
      //   this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      // lastModifiedBy: this.editForm.get(['lastModifiedBy']).value,
      // lastModifiedDate:
      //   this.editForm.get(['lastModifiedDate']).value != null
      //     ? moment(this.editForm.get(['lastModifiedDate']).value, DATE_TIME_FORMAT)
      //     : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISnowflakeConnection>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  testConnection() {
    this.testDisable = true;
    const connection = this.createFromForm();
    this.snowflakeConnectionService.testConnection(connection).subscribe(response => {
      if (response.body) {
        this.snowflakeConnection.valid = !!response.body;
        this.snowflakeConnectionService.update(this.snowflakeConnection).subscribe(res => {});
        const smsg = 'snowpoleApp.sourceConnection.testConnectionSuccess';
        this.jhiAlertService.success(smsg);
        this.valid = true;
        this.testDisable = false;
      } else {
        this.snowflakeConnection.valid = !!response.body;
        this.snowflakeConnectionService.update(this.snowflakeConnection).subscribe(res => {});
        this.valid = false;
        const smsg = 'snowpoleApp.sourceConnection.testConnectionInvalid';
        this.jhiAlertService.error(smsg);
        this.testDisable = false;
      }
    });
  }
}
