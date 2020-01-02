import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IMigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessService } from 'app/entities/migration-process/migration-process.service';

@Component({
  selector: 'jhi-report',
  templateUrl: './migration-process-status.component.html'
})
export class MigrationProcessStatusComponent implements OnInit {
  migrationProcess: IMigrationProcess;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private migrationProcessService: MigrationProcessService, protected router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      this.migrationProcess = migrationProcess;
      this.refresh();
    });
  }

  refresh() {
    this.loading = true;
    this.migrationProcessService.getProcessStatus(this.migrationProcess.id).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  viewJobReport(processId, batchId) {
    this.router.navigate(['/migration-process', processId, 'history', batchId, 'view']);
  }

  previousState() {
    window.history.back();
  }
}
