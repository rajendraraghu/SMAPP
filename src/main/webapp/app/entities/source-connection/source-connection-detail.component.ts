import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISourceConnection } from 'app/shared/model/source-connection.model';
import { SourceConnectionService } from './source-connection.service';

@Component({
  selector: 'jhi-source-connection-detail',
  templateUrl: './source-connection-detail.component.html'
})
export class SourceConnectionDetailComponent implements OnInit {
  sourceConnection: ISourceConnection;

  constructor(protected activatedRoute: ActivatedRoute, private sourceConnectionService: SourceConnectionService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sourceConnection }) => {
      this.sourceConnection = sourceConnection;
    });
  }

  testConnection(connection) {
    this.sourceConnectionService.testConnection(connection).subscribe(response => {
      console.log(response.body);
      if (connection.valid !== response.body) {
        connection.valid = response.body;
        this.sourceConnectionService.update(connection).subscribe(res => {});
      }
    });
  }

  previousState() {
    window.history.back();
  }
}
