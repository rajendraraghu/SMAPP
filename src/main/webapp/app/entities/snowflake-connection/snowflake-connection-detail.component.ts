import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISnowflakeConnection } from 'app/shared/model/snowflake-connection.model';
import { SnowflakeConnectionService } from './snowflake-connection.service';

@Component({
  selector: 'jhi-snowflake-connection-detail',
  templateUrl: './snowflake-connection-detail.component.html'
})
export class SnowflakeConnectionDetailComponent implements OnInit {
  snowflakeConnection: ISnowflakeConnection;

  constructor(protected activatedRoute: ActivatedRoute, private snowflakeConnectionService: SnowflakeConnectionService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ snowflakeConnection }) => {
      this.snowflakeConnection = snowflakeConnection;
    });
  }

  previousState() {
    window.history.back();
  }

  testConnection(connection) {
    this.snowflakeConnectionService.testConnection(connection).subscribe(response => {
      console.log(response.body);
      if (connection.valid !== response.body) {
        connection.valid = response.body;
        this.snowflakeConnectionService.update(connection).subscribe(res => {});
      }
    });
  }
}
