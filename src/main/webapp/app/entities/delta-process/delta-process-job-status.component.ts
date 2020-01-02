import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { DeltaProcessService } from 'app/entities/delta-process/delta-process.service';
import { IDeltaProcessStatus } from 'app/shared/model/delta-process-status.model';

@Component({
  selector: 'jhi-report',
  templateUrl: './delta-process-job-status.component.html'
})
export class DeltaProcessJobStatusComponent implements OnInit {
  deltaProcessProcessStatus: IDeltaProcessStatus;
  jId: number;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private deltaProcessService: DeltaProcessService, protected router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.jId = this.activatedRoute.snapshot.params.jId;
    console.log(this.jId);
    this.refresh();
  }

  refresh() {
    this.loading = true;
    this.deltaProcessService.getJobStatus(this.jId).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  previousState() {
    window.history.back();
  }
}
