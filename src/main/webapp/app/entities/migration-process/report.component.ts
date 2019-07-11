import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessService } from 'app/entities/migration-process/migration-process.service';

@Component({
  selector: 'jhi-report',
  templateUrl: './report.component.html'
})
export class ReportComponent implements OnInit {
  migrationProcess: IMigrationProcess;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private migrationProcessService: MigrationProcessService) {}

  ngOnInit() {
    this.loading = true;
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      this.migrationProcess = migrationProcess;
      this.refresh();
    });
  }

  refresh() {
    this.loading = true;
    this.migrationProcessService.getReports(this.migrationProcess).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  previousState() {
    window.history.back();
  }
}
