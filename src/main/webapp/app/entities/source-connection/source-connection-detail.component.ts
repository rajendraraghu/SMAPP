import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISourceConnection } from 'app/shared/model/source-connection.model';
import { SourceConnectionService } from './source-connection.service';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-source-connection-detail',
  templateUrl: './source-connection-detail.component.html'
})
export class SourceConnectionDetailComponent implements OnInit {
  sourceConnection: ISourceConnection;
  disable: boolean;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private sourceConnectionService: SourceConnectionService,
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sourceConnection }) => {
      this.sourceConnection = sourceConnection;
    });
  }

  testConnection() {
    this.disable = true;
    this.sourceConnectionService.testConnection(this.sourceConnection).subscribe(response => {
      if (response.body) {
        const smsg = 'snowpoleApp.sourceConnection.testConnectionSuccess';
        this.jhiAlertService.success(smsg);
        this.sourceConnection.valid = !!response.body;
        this.sourceConnectionService.update(this.sourceConnection).subscribe(res => {});
        this.disable = false;
      } else {
        const smsg = 'snowpoleApp.sourceConnection.testConnectionInvalid';
        this.jhiAlertService.error(smsg);
        this.sourceConnection.valid = !!response.body;
        this.sourceConnectionService.update(this.sourceConnection).subscribe(res => {});
        this.disable = false;
      }
    });
  }

  previousState() {
    window.history.back();
  }
}
