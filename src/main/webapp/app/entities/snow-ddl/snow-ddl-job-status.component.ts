import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SnowDDLService } from 'app/entities/snow-ddl/snow-ddl.service';
import { ISnowDDLProcessStatus } from 'app/shared/model/snow-ddl-process-status.model';

@Component({
  selector: 'jhi-report',
  templateUrl: './snow-ddl-job-status.component.html'
})
export class SnowDDLJobStatusComponent implements OnInit {
  snowDDLProcessStatus: ISnowDDLProcessStatus;
  jId: number;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private snowDDLService: SnowDDLService, protected router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.jId = this.activatedRoute.snapshot.params.jId;
    console.log(this.jId);
    this.refresh();
  }

  refresh() {
    this.loading = true;
    this.snowDDLService.getJobStatus(this.jId).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  previousState() {
    window.history.back();
  }
}
