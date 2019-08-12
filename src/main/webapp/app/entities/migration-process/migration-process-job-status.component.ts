import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { MigrationProcessService } from 'app/entities/migration-process/migration-process.service';
import { IMigrationProcessStatus } from 'app/shared/model/migration-process-status.model';

@Component({
  selector: 'jhi-report',
  templateUrl: './migration-process-job-status.component.html'
})
export class MigrationProcessJobStatusComponent implements OnInit {
  migrationProcessStatus: IMigrationProcessStatus;
  jId: number;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private migrationProcessService: MigrationProcessService, protected router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.jId = this.activatedRoute.snapshot.params.jId;
    console.log(this.jId);
    this.refresh();
  }

  refresh() {
    this.loading = true;
    this.migrationProcessService.getJobStatus(this.jId).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  previousState() {
    window.history.back();
  }
}
