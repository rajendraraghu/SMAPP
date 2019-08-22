import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISnowParse, SnowParse } from 'app/shared/model/snow-parse.model';
import { SnowParseService } from './snow-parse.service';
import { ISourceConnection } from 'app/shared/model/source-connection.model';
import { SourceConnectionService } from 'app/entities/source-connection';
import { ISnowflakeConnection } from 'app/shared/model/snowflake-connection.model';
import { SnowflakeConnectionService } from 'app/entities/snowflake-connection';

@Component({
  selector: 'jhi-snow-parse-update',
  templateUrl: './snow-parse-update.component.html'
})
export class SnowParseUpdateComponent implements OnInit {
  isSaving: boolean;

  isSourceSelected: boolean;

  isTargetSelected: boolean;

  sourceconnections: ISourceConnection[];

  snowflakeconnections: ISnowflakeConnection[];

  sourceTypes: any[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [null, [Validators.maxLength(650)]],
    sourceType: [null, [Validators.required]],
    sourcePath: [],
    sourceConnectionId: [],
    snowflakeConnectionId: []
    // sourceConnectionId: [null, Validators.required],
    // snowflakeConnectionId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected snowParseService: SnowParseService,
    protected sourceConnectionService: SourceConnectionService,
    protected snowflakeConnectionService: SnowflakeConnectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.sourceTypes = ['Amazon S3', 'Local', 'Azure Storage'];
    this.activatedRoute.data.subscribe(({ snowParse }) => {
      this.updateForm(snowParse);
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

  updateForm(snowParse: ISnowParse) {
    this.editForm.patchValue({
      id: snowParse.id,
      name: snowParse.name,
      description: snowParse.description,
      sourceType: snowParse.sourceType,
      sourcePath: snowParse.sourcePath,
      // createdBy: snowDDL.createdBy,
      // createdDate: snowDDL.createdDate != null ? snowDDL.createdDate.format(DATE_TIME_FORMAT) : null,
      // lastModifiedBy: snowDDL.lastModifiedBy,
      // lastModifiedDate: snowDDL.lastModifiedDate != null ? snowDDL.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      sourceConnectionId: snowParse.sourceConnectionId,
      snowflakeConnectionId: snowParse.snowflakeConnectionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const snowParse = this.createFromForm();
    if (snowParse.id !== undefined) {
      this.subscribeToSaveResponse(this.snowParseService.update(snowParse));
    } else {
      this.subscribeToSaveResponse(this.snowParseService.create(snowParse));
    }
  }

  onSourceSelection(event) {
    if (event.target.checked) {
      this.isSourceSelected = true;
    } else {
      this.isSourceSelected = false;
    }
  }

  onTargetSelection(event) {
    if (event.target.checked) {
      this.isTargetSelected = true;
    } else {
      this.isTargetSelected = false;
    }
  }

  private createFromForm(): ISnowParse {
    return {
      ...new SnowParse(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      sourceType: this.editForm.get(['sourceType']).value,
      sourcePath: this.editForm.get(['sourcePath']).value,
      sourceConnectionId: this.editForm.get(['sourceConnectionId']).value,
      snowflakeConnectionId: this.editForm.get(['snowflakeConnectionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISnowParse>>) {
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
