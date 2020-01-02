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
  disflat: boolean;
  diss3 = true;
  dislocal = true;
  disinternal = true;
  disexternal = true;
  sourceTypes: any[];
  serverTypes: any[];
  serverType: String;
  sourceType: string;
  sourcetypelc: string;
  localFilePath: string;
  testDisable: boolean;
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
    serverType: [],
    localFilePath: [],
    ftpHost: [],
    ftpPortNumber: [],
    ftpUserName: [],
    ftpPassword: [],
    username: [],
    password: [],
    database: [],
    host: [],
    portNumber: [],
    schema: [],
    // valid: [],
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
    this.sourceTypes = ['MySQL', 'Netezza', 'Teradata', 'Oracle', 'SQLServer', 'Flatfiles'];
    this.serverTypes = ['Local', 'InternalServer', 'ExternalServer'];
    this.activatedRoute.data.subscribe(({ sourceConnection }) => {
      this.sourceConnection = sourceConnection;
      this.updateForm(sourceConnection);
    });
  }

  display(disval: any) {
    if (disval === 'Flatfiles') {
      this.disflat = true;
      // this.disdat = true;
    } else {
      // this.disdat = false;
      this.disflat = false;
    }
  }

  displayserver(disserver: any) {
    switch (disserver) {
      case 'Local':
        this.dislocal = false;
        this.disexternal = true;
        this.disinternal = true;
        break;
      case 'InternalServer':
        this.disinternal = false;
        this.dislocal = true;
        this.disexternal = true;
        break;
      case 'ExternalServer':
        this.disinternal = true;
        this.dislocal = true;
        this.disexternal = false;
        break;
      default:
        this.disexternal = true;
        this.dislocal = true;
        this.disinternal = true;
        break;
    }
  }

  concatUrl() {
    this.localFilePath = this.editForm.get(['localFilePath']).value;
    this.sourceType = this.editForm.get(['sourceType']).value;
    (this.serverType = this.editForm.get(['serverType']).value),
      (this.sourcetypelc = this.sourceType ? this.sourceType.toLowerCase() : this.sourceType);
    this.host = this.editForm.get(['host']).value;
    this.portNumber = this.editForm.get(['portNumber']).value;
    this.dbname = this.editForm.get(['database']).value;
    switch (this.sourcetypelc) {
      case 'oracle':
        this.url = 'jdbc:' + this.sourcetypelc + ':thin:@' + this.host + ':' + this.portNumber + '/' + this.dbname;
        break;
      case 'sqlserver':
        this.url = 'jdbc:' + this.sourcetypelc + '://' + this.host + ':' + this.portNumber + ';databaseName=' + this.dbname;
        break;
      case 'teradata':
        this.url = 'jdbc:' + this.sourcetypelc + '://' + this.host;
        break;
      default:
        this.url = 'jdbc:' + this.sourcetypelc + '://' + this.host + ':' + this.portNumber + '/' + this.dbname;
        break;
    }
  }

  updateForm(sourceConnection: ISourceConnection) {
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
      localFilePath: sourceConnection.url,
      serverType: sourceConnection.database,
      valid: sourceConnection.valid,
      ftpHost: sourceConnection.host,
      ftpPortNumber: sourceConnection.portNumber,
      ftpUserName: sourceConnection.username,
      ftpPassword: sourceConnection.password
    });
    if (sourceConnection.sourceType === 'Flatfiles') {
      this.display(sourceConnection.sourceType);
      this.displayserver(sourceConnection.database);
    } else {
      this.display(sourceConnection.sourceType);
      // this.displayserver(sourceConnection.database);
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sourceConnection = this.createFromForm();
    // this.testConnection();
    sourceConnection.valid = sourceConnection.valid;
    if (sourceConnection.id !== undefined) {
      this.subscribeToSaveResponse(this.sourceConnectionService.update(sourceConnection));
    } else {
      this.subscribeToSaveResponse(this.sourceConnectionService.create(sourceConnection));
    }
  }

  private createFromForm(): ISourceConnection {
    this.concatUrl();
    if (this.sourceType === 'Flatfiles') {
      if (this.serverType === 'Local') {
        return {
          ...new SourceConnection(),
          id: this.editForm.get(['id']).value,
          name: this.editForm.get(['name']).value,
          description: this.editForm.get(['description']).value,
          sourceType: this.editForm.get(['sourceType']).value,
          host: '',
          portNumber: '',
          schema: '',
          database: this.editForm.get(['serverType']).value,
          url: this.editForm.get(['localFilePath']).value,
          username: '',
          password: '',
          valid: this.valid
        };
      } else {
        return {
          ...new SourceConnection(),
          id: this.editForm.get(['id']).value,
          name: this.editForm.get(['name']).value,
          description: this.editForm.get(['description']).value,
          sourceType: this.editForm.get(['sourceType']).value,
          host: this.editForm.get(['ftpHost']).value,
          portNumber: this.editForm.get(['ftpPortNumber']).value,
          schema: '',
          database: this.editForm.get(['serverType']).value,
          url: '',
          username: this.editForm.get(['ftpUserName']).value,
          password: this.editForm.get(['ftpPassword']).value,
          valid: this.valid
          // valid: this.editForm.get(['valid']).value
        };
      }
    } else {
      // this.concatUrl();
      return {
        ...new SourceConnection(),
        id: this.editForm.get(['id']).value,
        name: this.editForm.get(['name']).value,
        description: this.editForm.get(['description']).value,
        sourceType: this.editForm.get(['sourceType']).value,
        host: this.editForm.get(['host']).value,
        portNumber: this.editForm.get(['portNumber']).value,
        schema: this.editForm.get(['schema']).value,
        database: this.editForm.get(['database']).value,
        url: this.url,
        username: this.editForm.get(['username']).value,
        password: this.editForm.get(['password']).value,
        valid: this.valid
        // valid: this.editForm.get(['valid']).value
      };
    }
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
    this.testDisable = true;
    const connection = this.createFromForm();
    this.sourceConnectionService.testConnection(connection).subscribe(response => {
      if (response.body) {
        const smsg = 'snowpoleApp.sourceConnection.testConnectionSuccess';
        this.valid = true;
        this.jhiAlertService.success(smsg);
        this.sourceConnection.valid = !!response.body;
        this.sourceConnectionService.update(this.sourceConnection).subscribe(res => {});
        this.testDisable = false;
      } else {
        const smsg = 'snowpoleApp.sourceConnection.testConnectionInvalid';
        this.valid = false;
        this.jhiAlertService.error(smsg);
        this.sourceConnection.valid = !!response.body;
        this.sourceConnectionService.update(this.sourceConnection).subscribe(res => {});
        this.testDisable = false;
      }
      // if (connection.valid !== response.body) {
      //   connection.valid = !!response.body;
      //   const smsg = 'snowpoleApp.sourceConnection.testConnectionSuccess';
      //   this.jhiAlertService.success(smsg);
      // }
    });
  }
}
