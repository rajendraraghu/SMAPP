import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISourceConnection } from 'app/shared/model/source-connection.model';

@Component({
  selector: 'jhi-source-connection-detail',
  templateUrl: './source-connection-detail.component.html'
})
export class SourceConnectionDetailComponent implements OnInit {
  sourceConnection: ISourceConnection;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sourceConnection }) => {
      this.sourceConnection = sourceConnection;
    });
  }

  previousState() {
    window.history.back();
  }
}
