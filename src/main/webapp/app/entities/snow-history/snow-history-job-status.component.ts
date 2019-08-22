import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SnowHistoryService } from 'app/entities/snow-history/snow-history.service';
import { ISnowHistoryProcessStatus } from 'app/shared/model/snow-history-process-status.model';

@Component({
  selector: 'jhi-report',
  templateUrl: './snow-history-job-status.component.html'
})
export class SnowHistoryJobStatusComponent implements OnInit {
  snowHistoryProcessStatus: ISnowHistoryProcessStatus;
  jId: number;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private snowHistoryService: SnowHistoryService, protected router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.jId = this.activatedRoute.snapshot.params.jId;
    console.log(this.jId);
    this.refresh();
  }

  refresh() {
    this.loading = true;
    this.snowHistoryService.getJobStatus(this.jId).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  previousState() {
    window.history.back();
  }
}
