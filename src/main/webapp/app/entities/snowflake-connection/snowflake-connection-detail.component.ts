import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISnowflakeConnection } from 'app/shared/model/snowflake-connection.model';
import { SnowflakeConnectionService } from './snowflake-connection.service';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-snowflake-connection-detail',
  templateUrl: './snowflake-connection-detail.component.html'
})
export class SnowflakeConnectionDetailComponent implements OnInit {
  snowflakeConnection: ISnowflakeConnection;
  disable: boolean;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private snowflakeConnectionService: SnowflakeConnectionService,
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ snowflakeConnection }) => {
      this.snowflakeConnection = snowflakeConnection;
    });
  }

  previousState() {
    window.history.back();
  }

  testConnection() {
    this.disable = true;
    this.snowflakeConnectionService.testConnection(this.snowflakeConnection).subscribe(response => {
      if (response.body) {
        const smsg = 'snowpoleApp.sourceConnection.testConnectionSuccess';
        this.jhiAlertService.success(smsg);
        this.snowflakeConnection.valid = !!response.body;
        this.snowflakeConnectionService.update(this.snowflakeConnection).subscribe(res => {});
      } else {
        const smsg = 'snowpoleApp.sourceConnection.testConnectionInvalid';
        this.jhiAlertService.error(smsg);
        this.snowflakeConnection.valid = !!response.body;
        this.snowflakeConnectionService.update(this.snowflakeConnection).subscribe(res => {});
      }
    });
  }
}
