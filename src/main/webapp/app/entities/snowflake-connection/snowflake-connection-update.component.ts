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
  isSourceSelected: boolean;
  regionsId: any[];
  regionId: string;
  account: any;
  url: string;
  regaws: string;
  regazure: string;
  environment: string[];
  region: string;
  regExp: RegExp = /:\/\/(.*):(.*)\//;
  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    // url: [null, [Validators.required, Validators.maxLength(1200)]],
    username: [null, [Validators.required]],
    password: [null, [Validators.required]],
    acct: [],
    regionId: [],
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
    this.regionsId = [
      'AWS-US West (Oregon)',
      'AWS-US East (N. Virginia)',
      'AWS-Canada (Central)',
      'AWS-EU (Ireland)',
      'AWS-EU (Frankfurt)',
      'AWS-Asia Pacific (Singapore)',
      'AWS-Asia Pacific (Sydney)',
      'AZURE-East US 2',
      'AZURE-US Gov Virginia',
      'AZURE-Canada Central',
      'AZURE-West Europe',
      'AZURE-Australia East'
    ];
    this.activatedRoute.data.subscribe(({ snowflakeConnection }) => {
      this.updateForm(snowflakeConnection);
    });
  }

  concatUrl() {
    this.account = this.editForm.get(['acct']).value;
    this.regionId = this.editForm.get(['regionId']).value;
    this.environment = this.regionId.split('-', 1);
    console.log(this.environment);
    this.region = this.environment[1];
    console.log(this.region);
    if (this.environment[0] === 'AWS') {
      switch (this.regionId) {
        case 'AWS-US East (N. Virginia)':
          this.regaws = 'us-east-1';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regaws + '.snowflakecomputing.com';
          break;
        case 'AWS-Canada (Central)':
          this.regaws = 'ca-central-1';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regaws + '.snowflakecomputing.com';
          break;
        case 'AWS-EU (Ireland)':
          this.regaws = 'eu-west-1';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regaws + '.snowflakecomputing.com';
          break;
        case 'AWS-EU (Frankfurt)':
          this.regaws = 'eu-central-1';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regaws + '.snowflakecomputing.com';
          break;
        case 'AWS-Asia Pacific (Singapore)':
          this.regaws = 'ap-southeast-1';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regaws + '.snowflakecomputing.com';
          break;
        case 'AWS-Asia Pacific (Sydney)':
          this.regaws = 'ap-southeast-2';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regaws + '.snowflakecomputing.com';
          break;
        default:
          this.url = 'jdbc:snowflake://' + this.account + '.snowflakecomputing.com';
          break;
      }
    } else {
      switch (this.regionId) {
        case 'AZURE-East US 2':
          this.regazure = 'east-us-2';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regazure + '.azure' + '.snowflakecomputing.com';
          break;
        case 'AZURE-US Gov Virginia':
          this.regazure = 'us-gov-virginia';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regazure + '.azure' + '.snowflakecomputing.com';
          break;
        case 'AZURE-Canada Central':
          this.regazure = 'Canada Central';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regazure + '.azure' + '.snowflakecomputing.com';
          break;
        case 'AZURE-West Europe':
          this.regazure = 'west-europe';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regazure + '.azure' + '.snowflakecomputing.com';
          break;
        default:
          this.regazure = 'australia-east';
          this.url = 'jdbc:snowflake://' + this.account + '.' + this.regazure + '.azure' + '.snowflakecomputing.com';
          break;
      }
    }
  }

  onSourceSelection(event) {
    if (event.target.checked) {
      this.isSourceSelected = true;
    } else {
      this.isSourceSelected = false;
    }
  }

  updateForm(snowflakeConnection: ISnowflakeConnection) {
    this.editForm.patchValue({
      id: snowflakeConnection.id,
      name: snowflakeConnection.name,
      description: snowflakeConnection.description,
      regionId: snowflakeConnection.regionId,
      // url: snowflakeConnection.url,
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
    const snowflakeConnection = this.createFromForm();
    if (snowflakeConnection.id !== undefined) {
      this.subscribeToSaveResponse(this.snowflakeConnectionService.update(snowflakeConnection));
    } else {
      this.subscribeToSaveResponse(this.snowflakeConnectionService.create(snowflakeConnection));
    }
  }

  private createFromForm(): ISnowflakeConnection {
    this.concatUrl();
    return {
      ...new SnowflakeConnection(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      regionId: this.editForm.get(['regionId']).value,
      url: this.url,
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
    const connection = this.createFromForm();
    this.snowflakeConnectionService.testConnection(connection).subscribe(response => {
      console.log(response.body);
      if (!response.body) {
        const errorMessage = 'snowpoleApp.sourceConnection.invalid';
        this.jhiAlertService.error(errorMessage, null, null);
      }
      if (connection.valid !== response.body) {
        connection.valid = !!response.body;
        this.snowflakeConnectionService.update(connection).subscribe(res => {});
      }
    });
  }
}
