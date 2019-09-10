import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISourceConnection, SourceConnection } from 'app/shared/model/source-connection.model';
import { SourceConnectionService } from './source-connection.service';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-source-connection-update',
  templateUrl: './source-connection-update.component.html'
})
export class SourceConnectionUpdateComponent implements OnInit {
  sourceConnection: ISourceConnection;
  isSaving: boolean;
  sourceTypes: any[];
  sourcetype: string;
  host: string;
  portNumber: string;
  dbname: string;
  url: string;
  sliced: string[];
  regExp: RegExp = /:\/\/(.*):(.*)\//;
  valid: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.maxLength(650)]],
    sourceType: [null, [Validators.required]],
    // url: [null, [Validators.required, Validators.maxLength(1200)]],
    username: [null, [Validators.required]],
    password: [null, [Validators.required]],
    database: [null, [Validators.required]],
    host: [null, [Validators.required]],
    portNumber: [null, [Validators.required, Validators.maxLength(4)]],
    schema: [],
    valid: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: []
  });

  constructor(
    protected sourceConnectionService: SourceConnectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.valid = false;
    this.sourceTypes = ['MySQL', 'Netezza', 'Teradata', 'Oracle'];
    this.activatedRoute.data.subscribe(({ sourceConnection }) => {
      this.updateForm(sourceConnection);
      this.sourceConnection = sourceConnection;
    });
  }

  concatUrl() {
    this.sourcetype = this.editForm.get(['sourceType']).value;
    this.host = this.editForm.get(['host']).value;
    this.portNumber = this.editForm.get(['portNumber']).value;
    this.dbname = this.editForm.get(['database']).value;
    // this.url = 'jdbc:' + this.sourcetype + ':thin:@' + this.host + ':' + this.portNumber + '/' + this.dbname;
    this.url = 'jdbc:' + this.sourcetype + '://' + this.host + ':' + this.portNumber + '/' + this.dbname;
  }

  updateForm(sourceConnection: ISourceConnection) {
    // this.concatUrl();
    this.editForm.patchValue({
      id: sourceConnection.id,
      name: sourceConnection.name,
      description: sourceConnection.description,
      sourceType: sourceConnection.sourceType,
      username: sourceConnection.username,
      password: sourceConnection.password,
      database: sourceConnection.database,
      host: sourceConnection.host,
      portNumber: sourceConnection.portNumber,
      schema: sourceConnection.schema,
      valid: sourceConnection.valid
      // ,
      // createdBy: sourceConnection.createdBy,
      // createdDate: sourceConnection.createdDate != null ? sourceConnection.createdDate.format(DATE_TIME_FORMAT) : null,
      // lastModifiedBy: sourceConnection.lastModifiedBy,
      // lastModifiedDate: sourceConnection.lastModifiedDate != null ? sourceConnection.lastModifiedDate.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sourceConnection = this.createFromForm();
    sourceConnection.valid = this.valid;
    if (sourceConnection.id !== undefined) {
      this.subscribeToSaveResponse(this.sourceConnectionService.update(sourceConnection));
    } else {
      this.subscribeToSaveResponse(this.sourceConnectionService.create(sourceConnection));
    }
  }

  private createFromForm(): ISourceConnection {
    this.concatUrl();
    return {
      ...new SourceConnection(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      sourceType: this.editForm.get(['sourceType']).value,
      url: this.url,
      // url: this.editForm.get(['url']).value,
      username: this.editForm.get(['username']).value,
      password: this.editForm.get(['password']).value,
      database: this.editForm.get(['database']).value,
      host: this.editForm.get(['host']).value,
      portNumber: this.editForm.get(['portNumber']).value,
      schema: this.editForm.get(['schema']).value,
      valid: this.editForm.get(['valid']).value
      // ,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISourceConnection>>) {
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
    const connection = this.createFromForm();
    this.sourceConnectionService.testConnection(connection).subscribe(response => {
      if (response.body) {
        const smsg = 'snowpoleApp.sourceConnection.testConnectionSuccess';
        this.valid = true;
        this.jhiAlertService.success(smsg);
      } else {
        const smsg = 'snowpoleApp.sourceConnection.testConnectionInvalid';
        this.valid = false;
        this.jhiAlertService.error(smsg);
      }
      // if (connection.valid !== response.body) {
      //   connection.valid = !!response.body;
      //   const smsg = 'snowpoleApp.sourceConnection.testConnectionSuccess';
      //   this.jhiAlertService.success(smsg);
      // }
    });
  }
}
