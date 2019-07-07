import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMigrationProcess } from 'app/shared/model/migration-process.model';

@Component({
  selector: 'jhi-migration-process-detail',
  templateUrl: './migration-process-detail.component.html'
})
export class MigrationProcessDetailComponent implements OnInit {
  migrationProcess: IMigrationProcess;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      this.migrationProcess = migrationProcess;
    });
  }

  previousState() {
    window.history.back();
  }
}
