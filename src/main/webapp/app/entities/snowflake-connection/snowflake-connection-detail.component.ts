import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISnowflakeConnection } from 'app/shared/model/snowflake-connection.model';

@Component({
  selector: 'jhi-snowflake-connection-detail',
  templateUrl: './snowflake-connection-detail.component.html'
})
export class SnowflakeConnectionDetailComponent implements OnInit {
  snowflakeConnection: ISnowflakeConnection;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ snowflakeConnection }) => {
      this.snowflakeConnection = snowflakeConnection;
    });
  }

  previousState() {
    window.history.back();
  }
}
