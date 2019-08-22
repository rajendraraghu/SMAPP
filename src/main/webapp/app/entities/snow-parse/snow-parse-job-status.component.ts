import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SnowParseService } from 'app/entities/snow-parse/snow-parse.service';
import { ISnowParseProcessStatus } from 'app/shared/model/snow-parse-process-status.model';

@Component({
  selector: 'jhi-report',
  templateUrl: './snow-parse-job-status.component.html'
})
export class SnowParseJobStatusComponent implements OnInit {
  snowDDLProcessStatus: ISnowParseProcessStatus;
  jId: number;
  reports: any;
  loading: boolean;
  constructor(private activatedRoute: ActivatedRoute, private snowParseService: SnowParseService, protected router: Router) {}

  ngOnInit() {
    this.loading = true;
    this.jId = this.activatedRoute.snapshot.params.jId;
    console.log(this.jId);
    this.refresh();
  }

  refresh() {
    this.loading = true;
    this.snowParseService.getJobStatus(this.jId).subscribe(response => {
      this.reports = response.body;
      this.loading = false;
    });
  }

  previousState() {
    window.history.back();
  }
}
